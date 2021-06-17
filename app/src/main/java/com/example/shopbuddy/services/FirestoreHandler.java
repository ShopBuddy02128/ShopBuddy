package com.example.shopbuddy.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.ui.shoplist.AutocompleteAdapter;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

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

//        Log.e("bruh", "Firebase query");
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
                                doc.getString("imageUrl"));
                        list.add(shopListItem);
//                        Log.e("bruh", "List length: " + list.size());
//                        Log.e("bruh", list.get(0).toString());
                        Log.i("bruh", "Firestore returns: " + list.toString());

                        // update the adapter
                        AutocompleteAdapter newAdapter = new AutocompleteAdapter(frag.requireActivity(), list);

                        frag.ac.setAdapter(newAdapter);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
        Log.i("bruh", "return value: " +list.toString());
    }
}
