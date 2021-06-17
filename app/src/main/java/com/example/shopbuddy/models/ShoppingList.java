package com.example.shopbuddy.models;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ShoppingList {
    private String title;
    private String createdByUserId;
    GregorianCalendar creationDate;
    ArrayList<String> itemIds;
    double price;

    public ShoppingList(String title){
        this.title = title;
        itemIds = new ArrayList<>();
        creationDate = new GregorianCalendar();
        price = 0;
    }

    public ShoppingList(String title, String createdByUserId, double price) {
        this.title = title;
        this.createdByUserId = createdByUserId;
        this.creationDate = new GregorianCalendar();
        this.itemIds = new ArrayList<>();
        this.price = price;
    }

    public String getTitle(){
        return title;
    }

    public void addItem(String id){
        itemIds.add(id);
    }

    public int getSize(){
        return itemIds.size();
    }

    public ArrayList<String> getItems(){
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
