package com.example.shopbuddy.ui.shoplist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityItemViewBinding;
import com.example.shopbuddy.services.ImageLoadTask;

import java.io.IOException;


public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    ActivityItemViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = this.getIntent();

        if (i != null) {
            String name = i.getStringExtra("name");
            String brand = i.getStringExtra("brand");
            String price = i.getStringExtra("price");
            String qty = i.getStringExtra("qty");
            String imageUrl = i.getStringExtra("imageUrl");

            binding.itemviewName.setText(name);
            binding.itemviewBrand.setText(brand);
            binding.itemviewPrice.setText(price);
            binding.itemviewQty.setText(qty);


            Bitmap bmp = null;
            ImageLoadTask task = new ImageLoadTask(binding.itemviewImage);
            Log.i(TAG, "Url = " + imageUrl);
            task.execute(imageUrl);
        }
    }
}
