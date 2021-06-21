package com.example.shopbuddy.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.shoplist.AutocompleteAdapter;
import com.example.shopbuddy.ui.shoplist.ListAdapter;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public void addItemToShoppingList(String itemId, String shoppingListId, int orderNo) {
        // insert if key does not exist already
        Map<String, Object> updateVal = new HashMap<>();
        updateVal.put("itemIds."+itemId, 1);
        updateVal.put("itemOrder."+itemId, orderNo);
        db.collection("shoppingLists")
                .document(shoppingListId)
                .update(updateVal)
                .addOnSuccessListener(t -> {
                    // update listview
                    getShoppingListContents(shoppingListId);
                });
    }

    public void deleteItemFromShoppingList(String itemId, double itemPrice, String shoppingListId) {
        db.collection("shoppingLists")
                .document(shoppingListId)
                .get()
                .addOnSuccessListener(doc -> {
                    HashMap itemIds = (HashMap<String,Long>) doc.get("itemIds");
                    HashMap itemOrder = (HashMap<String,Long>) doc.get("itemOrder");

                    Map<String, Object> updateVal = new HashMap<>();
                    double price = itemPrice;
                    long qty = (long) itemIds.get(itemId);
                    boolean negativeItemAdjustment = false;

                    itemIds.remove(itemId);
                    itemOrder.remove(itemId);

                    updateVal.put("itemIds", itemIds);
                    updateVal.put("itemOrder", itemOrder);

                    ToastService.makeToast("Removed item from list", Toast.LENGTH_SHORT);

                    db.collection("shoppingLists")
                            .document(shoppingListId)
                            .update(updateVal)
                            .addOnCompleteListener(unused -> {
                                // finally update shopping list price
                                updateShoppingListPrice(shoppingListId, price, negativeItemAdjustment, (int)qty);
                            });
                });
    }

    public void updateShoppingListPrice(String shoppingListId, double itemPrice, boolean plus, int qty) {
        db.collection("shoppingLists")
                .document(shoppingListId)
                .get()
                .addOnSuccessListener(t -> {
                    double price = t.getDouble("price");
                    if (plus)
                        price += itemPrice * qty;
                    else
                        price -= itemPrice * qty;
                    Map<String, Object> updateVal = new HashMap<>();
                    updateVal.put("price", price);

                    double finalPrice = price;
                    db.collection("shoppingLists")
                            .document(shoppingListId)
                            .update(updateVal)
                            .addOnSuccessListener(unused -> {
                                // update shopping list price
                                ShopListFragment.shoppingListPrice = finalPrice;
                            });
                });
    }

    public void updateShoppingListPrice(String shoppingListId, String userId, double itemPrice, boolean plus) {
        db.collection("shoppingLists")
                .document(shoppingListId)
                .get()
                .addOnSuccessListener(t -> {
                    if (!Objects.equals(t.getString("userId"), userId)) {
                        ToastService.makeToast("Insufficient rights", Toast.LENGTH_SHORT);
                        return;
                    }

                    double price = t.getDouble("price");
                    if (plus)
                        price += itemPrice;
                    else
                        price -= itemPrice;
                    Map<String, Object> updateVal = new HashMap<>();
                    updateVal.put("price", price);

                    double finalPrice = price;
                    db.collection("shoppingLists")
                            .document(shoppingListId)
                            .update(updateVal)
                            .addOnSuccessListener(unused -> {
                                // update shopping list price
                                ShopListFragment.shoppingListPrice = finalPrice;
                            });
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

                        // update the adapter
                        AutocompleteAdapter newAdapter = new AutocompleteAdapter(frag.requireActivity(), list);

                        frag.ac.setAdapter(newAdapter);
                        frag.acAdapter = newAdapter;
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void getShoppingListContents(String shoppingListId) {
        if (frag == null)
            return;
        if (frag.binding == null)
            return;

        ArrayList<ShopListItem> list = new ArrayList<>();
            db.collection("shoppingLists")
                    .document(shoppingListId)
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot doc = task.getResult();
                        ShoppingList shoppingList = new ShoppingList(
                                doc.getString("title"),
                                doc.getString("userId"),
                                doc.getDate("creationDate"),
                                (HashMap<String,Long>) doc.get("itemIds"),
                                (HashMap<String,Long>) doc.get("itemOrder"),
                                doc.getDouble("price"),
                                doc.getString("id"));
                        frag.shoppingList = shoppingList;

                        for (Map.Entry<String, Long> itemEntry : shoppingList.getItems().entrySet()) {
                            db.collection("items")
                                    .document(itemEntry.getKey())
                                    .get()
                                    .addOnCompleteListener(itemTask -> {
                                        DocumentSnapshot itemDoc = itemTask.getResult();
                                        ShopListItem curItem =  new ShopListItem(itemDoc.getString("name"),
                                                itemDoc.getString("brand"),
                                                itemDoc.getString("price"),
                                                itemEntry.getValue().toString(),
                                                itemDoc.getString("imageUrl"),
                                                itemDoc.getId());
                                        curItem.setOrderNo(shoppingList.getOrderNoOfItem(curItem.itemId));
                                        list.add(curItem);

                                        Log.i("bruh", "loaded " + itemDoc.getString("name"));

                                        // sort by orderNo
                                        Collections.sort(list);

                                        // update shopping list price
                                        double shoppingListPrice = doc.getDouble("price");
                                        ShopListFragment.shoppingListPrice = shoppingListPrice;
                                        frag.binding.totalPrice.setText("Total: " + new DecimalFormat("#.##").format(shoppingListPrice));

                                        // update the adapter
                                        ListAdapter newAdapter = new ListAdapter(frag.requireActivity(), list);
                                        frag.shopListItems = list;
                                        frag.binding.list.setAdapter(newAdapter);
                                    });
                        }
                    })
                    .addOnFailureListener(e -> ToastService.makeToast("" + e.getMessage(), Toast.LENGTH_SHORT));
        }

        public void updateQty(String shoppingListId, String itemId, String userId, boolean plus) {
            db.collection("shoppingLists")
                    .document(shoppingListId)
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot doc = task.getResult();
                        if (!Objects.equals(doc.getString("userId"), userId)) {
                            ToastService.makeToast("Insufficient rights", Toast.LENGTH_SHORT);
                            return;
                        }

                        int delta = plus ? 1 : -1;
                        long qty = ((HashMap<String,Long>)doc.get("itemIds")).get(itemId);
                        qty += delta;
                        Map<String, Object> updateVal = new HashMap<>();
                        updateVal.put("itemIds."+itemId, qty);
                        db.collection("shoppingLists")
                                .document(shoppingListId)
                                .update(updateVal);

                          ToastService.makeToast("Updated quantity", Toast.LENGTH_SHORT);
                    })
                    .addOnFailureListener(e -> ToastService.makeToast("" + e.getMessage(), Toast.LENGTH_SHORT));
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
                        ToastService.makeToast("Item no longer exists in shopping list", Toast.LENGTH_SHORT);
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
                                    ToastService.makeToast("Failed to create new document for user", Toast.LENGTH_SHORT);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    ToastService.makeToast("Failed to ensure user has list", Toast.LENGTH_SHORT);
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
                    ToastService.makeToast("Failed to get discounts", Toast.LENGTH_SHORT);
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
        db.collection("discountAlarmsForUsers")
                .document(userId)
                .get()
                .addOnCompleteListener(t -> {
                    ArrayList<String> alarmItems = (ArrayList<String>) t.getResult().get("items");
                    alarmItems.add(itemName);

                    Map<String, Object> itemsMap = new HashMap<>();
                    itemsMap.put("items", alarmItems);

                    db.collection("discountAlarmsForUsers")
                            .document(userId)
                            .update(itemsMap)
                            .addOnCompleteListener(l -> {
                                ToastService.makeToast("Added alarm for " + itemName, Toast.LENGTH_SHORT);
                            });
                });
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
                                    ToastService.makeToast("Failed to create new document for user", Toast.LENGTH_SHORT);
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
                    ToastService.makeToast("Failed to ensure user has list", Toast.LENGTH_SHORT);
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
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }
}
