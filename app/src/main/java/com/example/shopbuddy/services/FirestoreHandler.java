package com.example.shopbuddy.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.shoplist.AutocompleteAdapter;
import com.example.shopbuddy.ui.shoplist.ListAdapter;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FirestoreHandler {
    FirebaseFirestore db;
    ShopListFragment frag;
    Context context;

    public FirestoreHandler() {
        initFirebaseConn();
    }

    public FirestoreHandler(Context context, ShopListFragment frag) {
        initFirebaseConn();
        this.context = context;
        this.frag = frag;
    }

    public void initFirebaseConn() {
        db = FirebaseFirestore.getInstance();
    }

    public void addItemToShoppingList(String itemId, String shoppingListId, double itemPrice, int orderNo) {
        AtomicInteger listLength = new AtomicInteger();

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentReference listRef = db.collection("shoppingLists").document(shoppingListId);
            DocumentSnapshot listSnapshot = transaction.get(listRef);

            HashMap<String, Long> itemIds = (HashMap<String, Long>)listSnapshot.get("itemIds");

            // insert if key does not exist already
            if (!itemIds.containsKey(itemId)) {
                Map<String, Object> updateVals = new HashMap<>();
                updateVals.put("itemIds." + itemId, 1);
                updateVals.put("itemOrder." + itemId, orderNo);

                double totalPrice = roundToTwoDecimals(listSnapshot.getDouble("price"));
                totalPrice += itemPrice;
                updateVals.put("price", roundToTwoDecimals(totalPrice));
                transaction.update(listRef, updateVals);
            }

            listLength.set(itemIds.size());
            return null;
        }).addOnSuccessListener(l -> {
            ToastService.makeToast("Tilføjet vare til liste", Toast.LENGTH_SHORT);
            getShoppingListContentsTransaction(shoppingListId, listLength.intValue() - 1);
        }).addOnFailureListener(l -> {
            ToastService.makeToast("Kunne ikke hente indkøbsliste", Toast.LENGTH_SHORT);
            logTransactionError(l);
        });
    }


    // firestore on its own does not offer the greatest querying - this only looks for prefixes
    public void queryForSuggestions(String queryString) {
        ArrayList<ShopListItem> list = new ArrayList<>();
        if (queryString.length() == 0)
            return;

        queryString = queryString.toLowerCase();
        db.collection("items")
                .whereGreaterThanOrEqualTo("name", queryString)
                .whereLessThan("name", queryString + '\uf8ff')
                .get()
                .addOnCompleteListener(task -> {
                    for (DocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        ShopListItem shopListItem = new ShopListItem(
                                doc.getString("name"),
                                doc.getString("brand"),
                                doc.getString("price"),
                                "0",
                                doc.getString("imageUrl"),
                                doc.getId());
                        list.add(shopListItem);
                    }
                    // update the adapter
                    AutocompleteAdapter newAdapter = new AutocompleteAdapter(frag.requireActivity(), list);

                    frag.ac.setAdapter(newAdapter);
                    frag.acAdapter = newAdapter;
                    frag.acAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // scrollToIndex is for scrolling to the right index when the view has been updated, since this has to be done inside the async lambda
    public void getShoppingListContentsTransaction(String shoppingListId, int scrollToIndex) {
        if (frag == null)
            return;
        if (frag.binding == null)
            return;

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentReference listRef = db
                    .collection("shoppingLists")
                    .document(shoppingListId);
            DocumentSnapshot listSnapshot = transaction.get(listRef);
            ShoppingList shoppingList = new ShoppingList(
                    listSnapshot.getString("title"),
                    listSnapshot.getString("userId"),
                    listSnapshot.getDate("creationDate"),
                    (HashMap<String,Long>) listSnapshot.get("itemIds"),
                    (HashMap<String,Long>) listSnapshot.get("itemOrder"),
                    listSnapshot.getDouble("price"),
                    listSnapshot.getString("id"));
            frag.shoppingList = shoppingList;

            ArrayList<ShopListItem> list = new ArrayList<>();
            CollectionReference itemsRef = db.collection("items");
            DocumentReference itemRef;
            DocumentSnapshot itemSnapshot;
            for (Map.Entry<String, Long> itemEntry : shoppingList.getItems().entrySet()) {

                itemRef = itemsRef.document(itemEntry.getKey());
                itemSnapshot = transaction.get(itemRef);

                ShopListItem curItem =  new ShopListItem(itemSnapshot.getString("name"),
                        itemSnapshot.getString("brand"),
                        itemSnapshot.getString("price"),
                        itemEntry.getValue().toString(),
                        itemSnapshot.getString("imageUrl"),
                        itemSnapshot.getId());
                curItem.setOrderNo(shoppingList.getOrderNoOfItem(curItem.itemId));

                list.add(curItem);
            }

            // sort by orderNo through Comparable interface
            Collections.sort(list);

            // update shopping list price
            double shoppingListPrice = listSnapshot.getDouble("price");
            ShopListFragment.shoppingListPrice = shoppingListPrice;
            frag.requireActivity().runOnUiThread(() -> {
                frag.binding.totalPrice.setText("Total: " + new DecimalFormat("#.##").format(shoppingListPrice));

                // update the adapter
                ListAdapter newAdapter = new ListAdapter(frag.requireActivity(), list);
                frag.shopListItems = list;
                frag.binding.list.setAdapter(newAdapter);
                if (scrollToIndex != -1)
                    frag.binding.list.setSelection(scrollToIndex);
            });

            return null;
        }).addOnSuccessListener(l -> {
//            ToastService.makeToast("Hentet indkøbsliste", Toast.LENGTH_SHORT);
        }).addOnFailureListener(l -> {
            ToastService.makeToast("Kunne ikke hente indkøbsliste", Toast.LENGTH_SHORT);
            logTransactionError(l);
        });
    }

    public void updateQtyTransaction(String shoppingListId, String itemId, String userId, boolean plus) {
        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentReference listRef = db
                    .collection("shoppingLists")
                    .document(shoppingListId);
            DocumentSnapshot listSnapshot = transaction.get(listRef);

            CollectionReference itemsRef = db.collection("items");
            DocumentReference itemRef = itemsRef.document(itemId);
            DocumentSnapshot itemSnapshot = transaction.get(itemRef);

            HashMap<String, Object> updateVals = new HashMap<>();

            int delta = plus ? 1 : -1; // if plus, plus has been pressed -> add, otherwise minus -> subtract
            long qty = ((HashMap<String,Long>)listSnapshot.get("itemIds")).get(itemId);
            qty += delta;
            updateVals.put("itemIds."+itemId, qty);

            double totalPrice = roundToTwoDecimals(listSnapshot.getDouble("price"));
            double itemPrice = roundToTwoDecimals(Double.parseDouble(itemSnapshot.getString("price")));

            totalPrice += delta * itemPrice;

            updateVals.put("price", roundToTwoDecimals(totalPrice));

            transaction.update(listRef, updateVals);


            Log.i("transaction", "" + listSnapshot.getDouble("price") + " - " + delta + " * " + itemPrice + " = " + totalPrice);

            return null;
        }).addOnSuccessListener(l -> {
            ToastService.makeToastWithDuration("Opdateret antal",  800);
        }).addOnFailureListener(l -> {
            ToastService.makeToast("Kunne ikke opdatere antal", Toast.LENGTH_SHORT);
            logTransactionError(l);
        });
    }


    public void closeActivityIfItemNotInShoppingList(String itemId, String shoppingListId, Activity act) {
        Log.i("bruh", "starting query");
        db.collection("shoppingLists")
                .document(shoppingListId)
                .get()
                .addOnCompleteListener(t -> {
                    DocumentSnapshot doc = t.getResult();
                    HashMap<String, Long> items = (HashMap<String, Long>) doc.get("itemIds");
                    if (!items.containsKey(itemId)) {
                        act.finish();
                        ToastService.makeToast("Vare findes ikke længere i listen", Toast.LENGTH_SHORT);
                    }
                });
    }

    public void prepareAlarmListForUser(String userId) {
        db.collection("discountAlarmsForUsers")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && !task.getResult().exists()) {
                        // Create document for user that is empty
                        HashMap<String, List<String>> newDoc = new HashMap<>();
                        List<String> emptyList = new ArrayList<>();
                        newDoc.put("items", emptyList);
                        db.collection("discountAlarmsForUsers")
                                .document(userId)
                                .set(newDoc)
                                .addOnFailureListener(e -> {
                                    ToastService.makeToast("Kunne ikke oprette ny liste", Toast.LENGTH_SHORT);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    ToastService.makeToast("Kunne ikke få forbindelse", Toast.LENGTH_SHORT);
                });
    }

    public void getDiscountAlarmList(String userId, NotificationsFragment notifyFragment) {
        db.collection("discountAlarmsForUsers")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        ArrayList<String> items = (ArrayList<String>) doc.get("items");
                        notifyFragment.updateItemsList(items);
                    }
                })
                .addOnFailureListener(e -> {
                    ToastService.makeToast("Kunne ikke hente tilbud", Toast.LENGTH_SHORT);
                });
    }

    public void updateDiscountList(String userId, ArrayList<String> itemsList) {
        Map<String, Object> itemsMap = new HashMap<>();
        itemsMap.put("items", itemsList);

        db.collection("discountAlarmsForUsers")
                .document(userId)
                .update(itemsMap);
    }

    public void addAlarmForItem(String userId, String itemName) {
        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentReference alarmsRef = db
                    .collection("discountAlarmsForUsers")
                    .document(userId);
            DocumentSnapshot alarmsSnapshot = transaction.get(alarmsRef);

            ArrayList<String> alarmItems = (ArrayList<String>) alarmsSnapshot.get("items");
            alarmItems.add(itemName);

            Map<String, Object> itemsMap = new HashMap<>();
            itemsMap.put("items", alarmItems);

            transaction.update(alarmsRef, itemsMap);

            return null;
        }).addOnSuccessListener(l -> {
            ToastService.makeToast("Tilføjet alarm for " + itemName, Toast.LENGTH_SHORT);
        }).addOnFailureListener(this::logTransactionError);
    }

    public void prepareShoppingListCollectionForUser(String userId, String userEmail, ShopListFragment shoplistFragment) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && !task.getResult().exists()) {
                        // Create document for user that is empty
                        HashMap<String, Object> newDoc = new HashMap<>();
                        List<String> emptyList = new ArrayList<>();
                        newDoc.put("shoppingLists", emptyList);
                        newDoc.put("userEmail", userEmail);
                        db.collection("users")
                                .document(userId)
                                .set(newDoc)
                                .addOnCompleteListener(addUserTask -> {
                                    if(addUserTask.isSuccessful()){
                                        createShoppingListForUser(userId, shoplistFragment);
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    ToastService.makeToast("Kunne ikke oprette ny liste", Toast.LENGTH_SHORT);
                                });
                    } else if(task.isSuccessful()){
                        ArrayList<String> shoppingList = (ArrayList<String>) task.getResult().get("shoppingLists");
                        if(shoppingList.size() == 0) {
                            createShoppingListForUser(userId, shoplistFragment);
                        } else {
                            shoplistFragment.setShoppingListId(shoppingList.get(0));
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    ToastService.makeToast("Kunne ikke oprette forbindelse", Toast.LENGTH_SHORT);
                });
    }

    public void createShoppingListForUser(String userId, ShopListFragment shoplistFragment) {
        HashMap<String, Object> shoplist = new HashMap<>();
        shoplist.put("creationDate", new Date());
        shoplist.put("itemIds", new HashMap<String, Long>());
        shoplist.put("itemOrder", new HashMap<String, Long>());
        shoplist.put("price", 0L);
        shoplist.put("userId", userId);

        db.collection("shoppingLists")
                .add(shoplist)
                .addOnCompleteListener(task -> {
                    HashMap<String, Object> updateObject = new HashMap<>();
                    List<String> newShoppingList = new ArrayList<>();
                    newShoppingList.add(task.getResult().getId());
                    updateObject.put("shoppingLists", newShoppingList);

                    db.collection("users")
                            .document(userId)
                            .update(updateObject).addOnCompleteListener(createShoppingListTask -> {
                        if(createShoppingListTask.isSuccessful()) {
                            shoplistFragment.setShoppingListId(task.getResult().getId());
                        }
                    });
                })
                .addOnFailureListener(this::logTransactionError);
    }

    public void deleteItemTransaction(String shoppingListId, String itemId, double itemPrice, Activity activity) {
        db.runTransaction((Transaction.Function<Void>) transaction -> {
            Log.i("transaction", "executing DELETE transaction");
            DocumentReference listRef = db
                    .collection("shoppingLists")
                    .document(shoppingListId);
            DocumentSnapshot listSnapshot = transaction.get(listRef);

            HashMap<String,Long> itemIds = (HashMap<String,Long>) listSnapshot.get("itemIds");
            HashMap<String,Long> itemOrder = (HashMap<String,Long>) listSnapshot.get("itemOrder");

            Map<String, Object> updateVal = new HashMap<>();

            int subtractQty = itemIds.get(itemId).intValue();
            double newPrice = roundToTwoDecimals(listSnapshot.getDouble("price")) - subtractQty * itemPrice;
//            Log.i("transaction", "" + listSnapshot.getDouble("price") + " - " + subtractQty + " * " + itemPrice + " = " + newPrice);

            itemIds.remove(itemId);
            itemOrder.remove(itemId);

            updateVal.put("itemIds", itemIds);
            updateVal.put("itemOrder", itemOrder);

            transaction.update(listRef, updateVal);
            transaction.update(listRef, "price", newPrice);

            return null;
        }).addOnSuccessListener(l -> {
            ToastService.makeToast("Fjernet vare fra liste", Toast.LENGTH_SHORT);
            activity.finish();
        }).addOnFailureListener(this::logTransactionError);
    }

    double roundToTwoDecimals(double number) {
        return (int)(number*100) / 100.0;
    }

    void logTransactionError(Exception l) {
        Log.e("transaction", l.getLocalizedMessage());
        for (StackTraceElement s : l.getStackTrace())
            Log.e("transaction", "        " + s.toString());
    }

    // utility method for adding fake data for showcase
    void addNewDataToDatabase(ArrayList<ShopListItem> items) {
        WriteBatch batch = db.batch();
        CollectionReference itemsRef = db.collection("items");
        for (ShopListItem newItem : items) {
            DocumentReference newItemRef = itemsRef.document();
            Map<String, Object> newItemMap =  new HashMap<>();
            newItemMap.put("name", newItem.name);
            newItemMap.put("brand", newItem.brand);
            newItemMap.put("price", newItem.price);
            newItemMap.put("qty", 0);
            newItemMap.put("imageUrl", newItem.imageUrl);
            batch.set(newItemRef, newItemMap);
        }

        batch.commit().addOnFailureListener(this::logTransactionError);
    }

    public void getDiscountAlarmListFromAlarmReceiver(String userId) {
        db.collection("discountAlarmsForUsers")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        ArrayList<String> items = (ArrayList<String>) doc.get("items");

                        AlarmService.setCallsToReceive(items.size());
                        AlarmService.setReceivedCalls(0);
                        AlarmService.resetListOfItems();

                        for (String item : items) {
                            new DiscountSearchService(item).start();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    ToastService.makeToast("Kunne ikke hente tilbud", Toast.LENGTH_SHORT);
                });
    }
}