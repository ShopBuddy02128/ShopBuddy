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

    public DiscountItem(String title, Store store, double price, double oldPrice, String dateFrom, String dateTo) throws Exception {
        this.title = title;
        this.store = store;
        this.price = price;
        this.oldPrice = oldPrice;

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        this.validFrom = format.parse(dateFrom);
        this.validTo = format.parse(dateTo);

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


