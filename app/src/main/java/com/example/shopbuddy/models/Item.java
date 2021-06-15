package com.example.shopbuddy.models;

public class Item {
    public String name, brand, price, qty;
    public int imageId;

    public Item(String name, String brand, String price, String qty, int imageId) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageId = imageId;
    }
}
