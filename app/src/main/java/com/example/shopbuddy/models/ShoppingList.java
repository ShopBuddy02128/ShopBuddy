package com.example.shopbuddy.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ShoppingList {
    private String title, createdByUserId, id;
    GregorianCalendar creationDate;
    HashMap<String, Long> itemIds;
    double price;


    public ShoppingList(String title){
        this.title = title;
        itemIds = new HashMap<>();
        creationDate = new GregorianCalendar();
        price = 0;
    }

    public ShoppingList(String title, String createdByUserId, double price) {
        this.title = title;
        this.createdByUserId = createdByUserId;
        this.creationDate = new GregorianCalendar();
        this.itemIds = new HashMap<>();
        this.price = price;
    }

    public ShoppingList(String title,
                        String createdByUserId,
                        Date creationDate,
                        HashMap<String, Long> itemIds,
                        double price,
                        String id) {
        this.title = title;
        this.createdByUserId = createdByUserId;
        this.creationDate = new GregorianCalendar();
            this.creationDate.setTime(creationDate);;
        this.itemIds = itemIds;
        this.price = price;
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void addItem(String id, Long quantity){
        itemIds.put(id, quantity);
    }

    public int getSize(){
        return itemIds.size();
    }

    public HashMap<String,Long> getItems(){
        return itemIds;
    }

    public GregorianCalendar getCreationDate(){
        return creationDate;
    }

    public String getCreationDateAsString(){
        String output = "Created on: ";
        output += creationDate.get(GregorianCalendar.DAY_OF_MONTH) + "-";
        output += creationDate.get(GregorianCalendar.MONTH) + "-";
        output += creationDate.get(GregorianCalendar.YEAR) + " ";
        output += creationDate.get(GregorianCalendar.HOUR_OF_DAY) + ":";
        output += creationDate.get(GregorianCalendar.MINUTE) + ":";
        return output + creationDate.get(GregorianCalendar.SECOND);
    }

    public String getPrice(){
        return "10";
    }
}
