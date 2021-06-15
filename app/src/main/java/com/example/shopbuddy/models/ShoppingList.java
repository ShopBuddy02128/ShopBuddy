package com.example.shopbuddy.models;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ShoppingList {
    private String title;
    GregorianCalendar creationDate;
    ArrayList<Item> items;

    public ShoppingList(String title){
        this.title = title;
        items = new ArrayList<>();
        creationDate = new GregorianCalendar();
    }

    public String getTitle(){
        return title;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public int getSize(){
        return items.size();
    }

    public ArrayList<Item> getItems(){
        return items;
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
}
