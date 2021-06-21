package com.example.shopbuddy.models;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShopListItem implements Comparable<ShopListItem> {
    public String name, brand, price, qty, imageUrl, itemId;
    public Date validTo = null;
    public String oldPrice = "0";
    private int orderNo;

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public ShopListItem(String name,
                        String brand,
                        String price,
                        String qty,
                        String imageUrl,
                        String itemId) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
        this.itemId = itemId;
    }

    public ShopListItem(String name,
                        String brand,
                        String price,
                        String qty,
                        String imageUrl,
                        String itemId,
                        int orderNo) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
        this.itemId = itemId;
        this.orderNo = orderNo;
    }

    public ShopListItem(String name,
                        String brand,
                        String price,
                        String qty,
                        String imageUrl,
                        String itemId,
                        String oldPrice,
                        String validTo){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.oldPrice = oldPrice;
        this.qty = qty;
        this.imageUrl = imageUrl;
        this.itemId = itemId;

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.validTo = format.parse(validTo);
            Log.i("DINFAR", this.validTo == null ? "validto is null" : "validto is not null");
        }catch(ParseException e){
            e.printStackTrace();
            Log.i("DINFAR", "Error in parsing din far");
        }

    }

    public boolean stillValid() {
        if(validTo == null) return true;
        Date current = new Date();
        int result = current.compareTo(validTo);
        return result > 0 ? false : true;
    }


    @Override
    public int compareTo(ShopListItem otherItem) {
        return this.orderNo <= otherItem.getOrderNo() ? -1 : 1;
    }

    @Override
    public String toString(){
        String string = "Item: \n";
        string += "name: " + (name == ""? "no name" : name) + "\n";
        string += "price: " + (price == "0.0"? "no price" : price) + "\n";
        string += "oldprice: " + (oldPrice == "0"? "no oldprice" : oldPrice) + "\n";
        string += "imageUrl: " + (imageUrl == ""? "no url" : imageUrl) +"\n";
        return string;
    }
}
