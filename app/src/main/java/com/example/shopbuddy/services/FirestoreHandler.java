package com.example.shopbuddy.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.ui.shoplist.AutocompleteAdapter;
import com.example.shopbuddy.ui.shoplist.ListAdapter;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    public void updateShoppingListPrice(String shoppingListId, double itemPrice, boolean plus) {
        db.collection("shoppingLists")
                .document(shoppingListId)
                .get()
                .addOnSuccessListener(t -> {
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
                                // update listview
                                getShoppingListContents(shoppingListId);
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
                                        frag.binding.listview.setAdapter(newAdapter);
                                    });
                        }
                    })
                    .addOnFailureListener(e -> ToastService.makeToast("" + e.getMessage(), Toast.LENGTH_SHORT));
        }

//    public ArrayList<ShopListItem> sortByShoppingListOrder(ArrayList<ShopListItem> items, ShoppingList shoppingList) {
//        ArrayList<>
//    }

        public void updateQty(String shoppingListId, String itemId, String userId, boolean plus) {
            db.collection("shoppingLists")
                    .document(shoppingListId)
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot doc = task.getResult();
                        if (!Objects.equals(doc.getString("userId"), userId)) {
                            Toast.makeText(context, "Insufficient rights", Toast.LENGTH_SHORT).show();
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
                        // TODO Toast message does not currently work:
                          ToastService.makeToast("Updated quantity", Toast.LENGTH_SHORT);
//                        Toast.makeText(context, "Updated quantity", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        }

    public void getDiscountAlarmList(String userId, NavigationActivity navAct) {
        db.collection("discountAlarmsForUsers")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        ArrayList<String> items = (ArrayList<String>) doc.get("items");
                        navAct.saveItems(items);
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
}
