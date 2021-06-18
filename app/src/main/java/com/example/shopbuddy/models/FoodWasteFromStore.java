package com.example.shopbuddy.models;
import java.util.ArrayList;

public class FoodWasteFromStore {

    ArrayList<DiscountItem> items = new ArrayList<>();
    Store store;

    public FoodWasteFromStore(){

    }

    public void addItem(DiscountItem item){
        items.add(item);
    }

    public void setStore(Store store){
        this.store = store;
    }

    public Store getStore(){
        return this.store;
    }

    public ArrayList<DiscountItem> getItems(){
        return items;
    }

    public String toString(){
        String string = "";

        for(DiscountItem item : items){
            string += item.getValidTo().toString();
        }

        return string;
    }
}