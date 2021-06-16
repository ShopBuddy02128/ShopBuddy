package com.example.shopbuddy.models;

public class Item {
    public String name, brand, price, qty, imageUrl;

    public Item(String name, String brand, String price, String qty, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
    }

    public String getTitle(){
        return this.name;
    }

    public String getPrice(){
        return this.price;
    }
}
