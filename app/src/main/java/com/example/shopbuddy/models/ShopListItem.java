package com.example.shopbuddy.models;

public class ShopListItem {
    public String name, brand, price, qty, imageUrl, itemId;

    public ShopListItem(String name,
                        String brand,
                        String price,
                        String qty,
                        String imageUrl,
                        String itemId) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "ShopListItem{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", qty='" + qty + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
