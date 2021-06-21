package com.example.shopbuddy.ui.shoplist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.databinding.ActivityItemViewBinding;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.utils.ImageLoadTask;
import com.example.shopbuddy.utils.TextFormatter;


public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    ActivityItemViewBinding binding;
    FirestoreHandler dbHandler;

    String shoppingListId, userId, itemId;
    Long qty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHandler = new FirestoreHandler();

        Intent i = this.getIntent();

        if (i != null) {
            String name = i.getStringExtra("name");
            String brand = i.getStringExtra("brand");
            String price = i.getStringExtra("price");
            qty = i.getLongExtra("qty", 1);
            String imageUrl = i.getStringExtra("imageUrl");

            shoppingListId = i.getStringExtra("shoppingListId");
            userId = i.getStringExtra("userId");
            itemId = i.getStringExtra("itemId");

            // if item has been deleted but is still present in list
            dbHandler.closeActivityIfNotInItemInShoppingList(itemId, shoppingListId, this);

            binding.itemviewName.setText(TextFormatter.toNameFormat(name));
            binding.itemviewBrand.setText(TextFormatter.toNameFormat(brand));
            binding.itemviewPrice.setText(price);
            binding.itemviewQty.setText(qty.toString());

            ImageLoadTask task = new ImageLoadTask(binding.itemviewImage);
            Log.i(TAG, "UID = " + userId);
            Log.i(TAG, "shoppingListId = " + shoppingListId);
            task.execute(imageUrl);
        }

        binding.itemQtyPlus.setOnClickListener(l -> {
            Log.i(TAG, "itemQtyPlus pressed");
            qty++;
            boolean plus = true;
            dbHandler.updateQty(shoppingListId, itemId, userId, plus);
            dbHandler.updateShoppingListPrice(shoppingListId, userId, Double.parseDouble(i.getStringExtra("price")), plus);
            binding.itemviewQty.setText(qty.toString());
        });

        binding.itemQtyMinus.setOnClickListener(l -> {
            Log.i(TAG, "itemQtyMinus pressed");
            if (qty == 0)
                return;
            qty--;
            boolean plus = false;
            dbHandler.updateQty(shoppingListId, itemId, userId, plus);
            dbHandler.updateShoppingListPrice(shoppingListId, userId, Double.parseDouble(i.getStringExtra("price")), plus);
            binding.itemviewQty.setText(qty.toString());
        });

        binding.itemDelete.setOnClickListener(l -> {
            Log.i(TAG, "Delete pressed");

            // TODO ask for confirmation before just deleting
            dbHandler.deleteItemFromShoppingList(itemId, Double.parseDouble(i.getStringExtra("price")), shoppingListId);

            finish();
        });

        binding.itemAddAlert.setOnClickListener(l -> {
            dbHandler.addAlarmForItem(userId, i.getStringExtra("name"));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
