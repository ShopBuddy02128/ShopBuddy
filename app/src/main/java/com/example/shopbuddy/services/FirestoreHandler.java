package com.example.shopbuddy.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbuddy.models.ShopListItem;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class FirestoreHandler {
    FirebaseFirestore db;
    Context context;

    public FirestoreHandler(Context context) {
        initFirebaseConn();
        this.context = context;
    }

    public void initFirebaseConn() {
        db = FirebaseFirestore.getInstance();
    }

    public void setContext(Context _context) {
        context = _context;
    }

    // firestore on its own does not offer the greatest querying - this only looks for prefixes
    public ArrayList<ShopListItem> queryForSuggestions(String queryString) {
        ArrayList<ShopListItem> list = new ArrayList<>();
        if (queryString.length() == 0)
            return list;

        Log.e("bruh", "Firebase query");
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
                        Log.e("bruh", "List length: " + list.size());
                        Log.e("bruh", list.get(0).toString());
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
        return list;
    }
}
