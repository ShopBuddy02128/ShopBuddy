package com.example.shopbuddy.ui.map;

import android.content.Intent;

import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityShopChosenBinding;
import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.services.DiscountForStoreService;

import java.util.List;


public class ShopChosenActivity extends AppCompatActivity{
    ActivityShopChosenBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopChosenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get the intent and data
        Intent intent = this.getIntent();

        String shopInfo = intent.getStringExtra("ShopInfo");
        int i = shopInfo.indexOf(' ');
        String shopName = shopInfo.substring(0, i);
        String shopAddress = shopInfo.substring(i);
        String shopOpeningHours = intent.getStringExtra("ShopOpeningHours");

        String id = intent.getStringExtra("ShopId");

        //Set the title of the activity
        getSupportActionBar().setTitle(shopName);

        if(shopName.toLowerCase().contains("netto")){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.Netto, null)));
        } else if (shopName.toLowerCase().contains("føtex")){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.Føtex, null)));
        } else if(shopName.toLowerCase().contains("bilka")) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.Bilka, null)));
        }

        //Set description
        binding.shopAdress.setText("Addresse: " + shopAddress);
        binding.shopOpening.setText(shopOpeningHours);

        try {
            new DiscountForStoreService(this, "Netto", 20).start();
        } catch (Exception e) {
            // Failed to get request, most likely caused by not calling a correct store option
        }

    }


    public void finishRequest(List<DiscountItem> listOfDiscountsForStore) {
        List<DiscountItem> items = listOfDiscountsForStore;
    }
}
