package com.example.shopbuddy.services;

import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.Store;
import com.example.shopbuddy.ui.map.SaleListFragment;
import com.example.shopbuddy.ui.map.ShopChosenActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscountForStoreService extends Thread{
    private final OkHttpClient client = new OkHttpClient();
    private String storeName;
    private String urlForStore;
    private SaleListFragment caller;

    public DiscountForStoreService(SaleListFragment caller, String store, int nrOfResults) throws Exception{
        if(!(store.equals("Netto") || store.equals("Bilka") || store.equals("Føtex")))
            throw new Exception("Store not available");
        this.storeName = store;
        this.caller = caller;
        switch (store) {
            case "Netto":
                urlForStore = "https://minetilbud.azure-api.net/cloudApi/web/Search/productsforcustomer/d0a3e9b6-222f-41f1-aa58-89b6a6e190fb?&numberOfResults=" + nrOfResults + "&isFeed=false";
                break;
            case "Bilka":
                urlForStore = "https://minetilbud.azure-api.net/cloudApi/web/Search/productsforcustomer/4ceeb7a1-2f94-4c40-a25a-82263bc08774?&numberOfResults=" + nrOfResults + "&isFeed=false";
                break;
            case "Føtex":
                urlForStore = "https://minetilbud.azure-api.net/cloudApi/web/Search/productsforcustomer/b5a1af22-c0f2-45d3-8da3-07f37ffdf894?&numberOfResults=" + nrOfResults + "&isFeed=false";
                break;
        }
    }

    public void run() {
        Request request = new Request.Builder()
                .url(this.urlForStore)
                .header("ocp-apim-subscription-key", "93ba15f29db644db87ada34d1efc2e2e")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            String resultString = response.body().string();

            caller.finishRequest(getItemsOnDiscountFromJSON(resultString));
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
        return new DiscountItem(title, new Store(store, null, null), price, 0, dateFrom, dateTo);
    }
}
