package com.example.shopbuddy.models;
import java.util.ArrayList;

public class FoodWasteFromStore {

    ArrayList<ShopListItem> items = new ArrayList<>();
    Store store;

    public FoodWasteFromStore(){

    }

    public void addItem(ShopListItem item){
        items.add(item);
    }

    public void setStore(Store store){
        this.store = store;
    }

    public Store getStore(){
        return this.store;
    }

    public ArrayList<ShopListItem> getItems(){
        return items;
    }

    public String toString(){
        String string = "Number of Items: " + items.size() + "\n";

        for(ShopListItem item : items){
            string += item.toString() + "\n";
        }

        return string;
    }
}