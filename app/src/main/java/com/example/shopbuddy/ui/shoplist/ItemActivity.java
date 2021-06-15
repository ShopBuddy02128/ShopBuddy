package com.example.shopbuddy.ui.shoplist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityItemViewBinding;


public class ItemActivity extends AppCompatActivity {

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
            int imageId = i.getIntExtra("imageid", R.drawable.haha);

            binding.itemviewName.setText(name);
            binding.itemviewBrand.setText(brand);
            binding.itemviewPrice.setText(price);
            binding.itemviewQty.setText(qty);
            binding.itemviewImage.setImageResource(imageId);
        }
    }
}
