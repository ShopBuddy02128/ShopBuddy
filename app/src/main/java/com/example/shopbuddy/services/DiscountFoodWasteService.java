package com.example.shopbuddy.services;

import com.example.shopbuddy.ui.foodwaste.FoodWasteFragment;
import com.example.shopbuddy.ui.map.ShopChosenActivity;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.utils.JSONReader;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscountFoodWasteService extends Thread{
    private final OkHttpClient client = new OkHttpClient();
    private String zipcode;
    private NavigationActivity caller;

    public DiscountFoodWasteService(NavigationActivity caller, String zipcode) throws Exception{
        this.zipcode = zipcode;
        this.caller = caller;
    }

    public void run() {
        Request request = new Request.Builder()
                .url("https://api.sallinggroup.com/v1/food-waste/?zip=" + zipcode)
                .header("Authorization", "Bearer dc92d87d-1f6d-4fcc-8a66-ddc95c5d9a24")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            String resultString = response.body().string();

            caller.setupFoodWasteFragment(JSONReader.getFoodWasteFromJson(resultString));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
