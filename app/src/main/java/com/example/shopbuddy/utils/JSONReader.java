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
                JSONArray clearances = getArray(clearanceObj,"clearances");
                JSONObject[] offerItems = new JSONObject[clearances.length()];

                //Get Array of items to be cleared
                for(int i=0; i<clearances.length(); i++){
                    offerItems[i] = clearances.getJSONObject(i);
                }

                //Add every item to the list
                for(JSONObject offerItem : offerItems){
                    ShopListItem item = getItemFromClearance(offerItem, store);
                    if(item != null) cl.addItem(item);
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
            JSONObject storeNode = getObject(clearanceObj,"store");

            String storeName = getString(storeNode, "name");
            String storeId = getString(storeNode,"id");

            JSONObject addressNode = getObject(storeNode, "address");

            String street = getString(addressNode,"street");
            String city = getString(addressNode,"city");
            String zip = getString(addressNode,"zip");
            String storeAddress = street + ", " + city + ", " + zip;

            Store store = new Store(storeName, storeAddress, storeId);
            return store;
    }

    //Getting an item from Anti Food Waste API
    private static ShopListItem getItemFromClearance(JSONObject offerItem, Store store) {
            //Separate offer from item
            JSONObject offer = getObject(offerItem, "offer");
            JSONObject item = getObject(offerItem, "product");
            Log.i("JSON", item.toString());

            //Get new and old price
            String newPrice = getDouble(offer,"newPrice") + "";
            String oldPrice = getDouble(offer,"originalPrice") + "";

            //Get quantity in stock
            String qty = getDouble(offer,"stock") + "";

            //Get dates
            String eDate = getString(offer,"endTime");
            Log.i("DINFAR", eDate);
            String sDate = getString(offer,"startTime");

            //Get name and id of item
            String name = getString(item,"description");
            String ean = getString(item,"ean");

            //Get imageUrl
            String imageUrl = getString(item,"image");

            ShopListItem sItem = new ShopListItem(name, "", newPrice, qty, imageUrl, ean, oldPrice, eDate);
            if(name == "") return null;
            return sItem;
    }

    private static String getString(JSONObject o, String tag){
        try{
            return o.getString(tag);
        }catch(JSONException e){
            e.printStackTrace();
            return "";
        }
    }

    private static double getDouble(JSONObject o, String tag){
        try{
            return o.getDouble(tag);
        }catch(JSONException e){
            e.printStackTrace();
            return 0;
        }
    }

    private static JSONObject getObject(JSONObject o, String tag){
        try{
            return o.getJSONObject(tag);
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static JSONArray getArray(JSONObject o, String tag){
        try{
            return o.getJSONArray(tag);
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

}

