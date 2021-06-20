package com.example.shopbuddy.services;

import com.example.shopbuddy.models.FoodWasteFromStore;

import java.util.ArrayList;

public interface ResultCallBack {
    void onResult(ArrayList<FoodWasteFromStore> data);
}
