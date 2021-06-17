package com.example.shopbuddy.models;

public class ShopListItem {
    public String name, brand, price, qty, imageUrl;

    public ShopListItem(String name, String brand, String price, String qty, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ShopListItem{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", qty='" + qty + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
