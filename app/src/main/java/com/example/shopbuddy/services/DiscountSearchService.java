package com.example.shopbuddy.services;

import com.example.shopbuddy.models.DiscountItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscountSearchService extends Thread{
    private final OkHttpClient client = new OkHttpClient();
    private String productName;
    public DiscountSearchService(String productName) {
        this.productName = productName.replaceAll(" ", "%20"); // Replace all spaces with http %20 space
    }

    public void run() {
        Request request = new Request.Builder()
                .url("https://minetilbud.azure-api.net/cloudApi/web/Search?search=" + productName + "&numberOfResults=20")
                .header("ocp-apim-subscription-key", "93ba15f29db644db87ada34d1efc2e2e")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            String resultString = response.body().string();
            getItemsOnDiscountFromJSON(resultString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public List<DiscountItem> getItemsOnDiscountFromJSON(String jsonString) {
        List<DiscountItem> discountItems = new ArrayList<DiscountItem>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray newJSON = jsonObject.getJSONArray("adverts");
            for(int i = 0; i < newJSON.length(); i++) {
                JSONObject newItem = newJSON.getJSONObject(i);
                if(newItem.getBoolean("showDate") && newItem.getDouble("price") > 0) { // Only add the item if it is an actual discount from a tilbudsavis with a new price.
                    discountItems.add(createDiscountObjectFromJsonObject(newItem));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return discountItems;
    }

    private DiscountItem createDiscountObjectFromJsonObject(JSONObject newItem) throws Exception{
        String title = newItem.getString("title");
        String store = newItem.getString("customerName");
        double price = newItem.getDouble("price");
        String dateFrom = newItem.getString("validFrom");
        String dateTo = newItem.getString("validTo");
        return new DiscountItem(title, store, price, dateFrom, dateTo);
    }
}
