package com.example.shopbuddy.services;

import android.graphics.Bitmap;

import com.example.shopbuddy.models.FoodWasteFromStore;

import java.util.ArrayList;

public interface FoodWasteCallBack {
    void onResult(ArrayList<FoodWasteFromStore> data);
}
