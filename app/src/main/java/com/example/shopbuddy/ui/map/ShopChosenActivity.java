package com.example.shopbuddy.ui.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.databinding.ActivityShopChosenBinding;

public class ShopChosenActivity extends AppCompatActivity {
    ActivityShopChosenBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopChosenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




    }
}
