package com.example.shopbuddy.models;

public class ShopListItem implements Comparable<ShopListItem> {
    public String name, brand, price, qty, imageUrl, itemId;
    private int orderNo;

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

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

    public ShopListItem(String name,
                        String brand,
                        String price,
                        String qty,
                        String imageUrl,
                        String itemId,
                        int orderNo) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
        this.itemId = itemId;
        this.orderNo = orderNo;
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

    @Override
    public int compareTo(ShopListItem otherItem) {
        return this.orderNo <= otherItem.getOrderNo() ? -1 : 1;
    }
}
