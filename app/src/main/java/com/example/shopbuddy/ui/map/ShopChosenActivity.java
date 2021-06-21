package com.example.shopbuddy.ui.map;

import android.content.Intent;

import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityShopChosenBinding;


public class ShopChosenActivity extends AppCompatActivity{
    ActivityShopChosenBinding binding;
    private String shopName;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopChosenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get the intent and data
        Intent intent = this.getIntent();

        String shopInfo = intent.getStringExtra("ShopInfo");
        int i = shopInfo.indexOf(' ');
        shopName = shopInfo.substring(0, i);
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


        // Set the fragment
        if(savedInstanceState == null){
            Fragment newFragment = new SaleListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.sale_list_fragment_container, newFragment).commit();
        }

    }

    public String getShopName() {
        return shopName;
    }
}
