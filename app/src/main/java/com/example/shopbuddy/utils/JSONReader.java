package com.example.shopbuddy.utils;
import android.util.Log;

import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.Store;

import org.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JSONReader {

    //Getting a comprehensive information object from Anti Food Waste API
    public static ArrayList<FoodWasteFromStore> getFoodWasteFromJson(String jsonString){
        try{
            //Get the initial JSON array including all the stores and their clearances
            JSONArray initialArray = new JSONArray(jsonString);
            JSONObject[] clearanceObs = new JSONObject[initialArray.length()];

            //Separate all the stores with their clearances
            for(int i=0; i<initialArray.length(); i++){
                clearanceObs[i] = initialArray.getJSONObject(i);
            }

            ArrayList<FoodWasteFromStore> cls = new ArrayList<>();

            //For each store, create a ClearancesFromStore object containing all the appropriate information
            for(JSONObject clearanceObj : clearanceObs){
                FoodWasteFromStore cl = new FoodWasteFromStore();

                //Get the store from the json object
                Store store = getStoreFromClearance(clearanceObj);
                cl.setStore(store);

                //Separate every item to be cleared
                JSONArray clearances = clearanceObj.getJSONArray("clearances");
                JSONObject[] offerItems = new JSONObject[clearances.length()];

                //Get Array of items to be cleared
                for(int i=0; i<clearances.length(); i++){
                    offerItems[i] = clearances.getJSONObject(i);
                }

                //Add every item to the list
                for(JSONObject offerItem : offerItems){
                    ShopListItem item = getItemFromClearance(offerItem, store);
                    cl.addItem(item);
                }

                //Add every list to the list
                cls.add(cl);

            }

            return cls;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    //Getting the store from Anti Food Waste API
    private static Store getStoreFromClearance(JSONObject clearanceObj) {
        try {
            JSONObject storeNode = clearanceObj.getJSONObject("store");

            String storeName = storeNode.getString("name");
            String storeId = storeNode.getString("id");

            JSONObject addressNode = storeNode.getJSONObject("address");

            String street = addressNode.getString("street");
            String city = addressNode.getString("city");
            String zip = addressNode.getString("zip");
            String storeAddress = street + ", " + city + ", " + zip;

            Store store = new Store(storeName, storeAddress, storeId);
            return store;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    //Getting an item from Anti Food Waste API
    private static ShopListItem getItemFromClearance(JSONObject offerItem, Store store) {
        try{
            //Separate offer from item
            JSONObject offer = offerItem.getJSONObject("offer");
            JSONObject item = offerItem.getJSONObject("product");

            //Get new and old price
            String newPrice = offer.getDouble("newPrice") + "";
            String oldPrice = offer.getDouble("originalPrice") + "";

            //Get quantity in stock
            String qty = offer.getDouble("stock") + "";

            //Get dates
            String eDate = offer.getString("endTime");
            Log.i("DINFAR", eDate);
            String sDate = offer.getString("startTime");

            //Get name and id of item
            String name = item.getString("description");
            String ean = item.getString("ean");

            //Get imageUrl
            String imageUrl = item.getString("image");

            ShopListItem sItem = new ShopListItem(name, "", newPrice, "1", imageUrl, ean, oldPrice, eDate);
            return sItem;

        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}

