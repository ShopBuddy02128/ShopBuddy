package com.example.shopbuddy.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.ui.shoplist.AutocompleteAdapter;
import com.example.shopbuddy.ui.shoplist.ListAdapter;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FirestoreHandler {
    FirebaseFirestore db;
    ShopListFragment frag;
    Context context;

    public FirestoreHandler(Context context, ShopListFragment frag) {
        initFirebaseConn();
        this.context = context;
        this.frag = frag;
    }

    public void initFirebaseConn() {
        db = FirebaseFirestore.getInstance();
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
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void getShoppingListContents(String shoppingListId) {
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
                                doc.getDouble("price"),
                                doc.getString("id"));
                        for (Map.Entry<String, Long> itemEntry : shoppingList.getItems().entrySet()) {
                            db.collection("items")
                                    .document(itemEntry.getKey())
                                    .get()
                                    .addOnCompleteListener(itemTask -> {
                                        DocumentSnapshot itemDoc = itemTask.getResult();
                                        list.add(
                                              new ShopListItem(itemDoc.getString("name"),
                                                      itemDoc.getString("brand"),
                                                      itemDoc.getString("price"),
                                                      itemEntry.getValue().toString(),
                                                      itemDoc.getString("imageUrl"),
                                                      itemDoc.getId())
                                        );
                                        Log.i("bruh", "bruh");
                                    });
                        }
                        // update the adapter
                        ListAdapter newAdapter = new ListAdapter(frag.requireActivity(), list);
                        frag.shoppingList = shoppingList;
                        frag.shopListItems = list;

                        frag.listAdapter = newAdapter;
                        frag.listAdapter.notifyDataSetChanged();

                        frag.binding.listview.setAdapter(newAdapter);
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
}
