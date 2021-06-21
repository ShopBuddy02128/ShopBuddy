package com.example.shopbuddy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DiscountItem {
    private double price;
    private double oldPrice;
    private Date validFrom;
    private Date validTo;
    private String title;
    private Store store;
    private String brand;

    public DiscountItem(String title, Store store, double price, double oldPrice, String dateFrom, String dateTo) throws Exception {
        this.store = store;
        this.price = price;
        this.oldPrice = oldPrice;

        int i = title.indexOf(' ');
        brand = title.substring(0,i);
        this.title = title.substring(i+1);

    }

    public DiscountItem(String title, Store store, double price, double oldPrice, Date dateFrom, Date dateTo) {
        this.title = title;
        this.store = store;
        this.price = price;
        this.oldPrice = oldPrice;

        this.validFrom = dateFrom;
        this.validTo = dateTo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getBrand() {
        return brand;
    }

    public double getOldPrice(){ return oldPrice; }

    public void setTitle(String title) {
        this.title = title;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public boolean stillValid() {
        Date current = new Date();
        int result = current.compareTo(validTo);
        return result > 0 ? false : true;
    }


    public String toString(){
        return this.title;
    }

}


