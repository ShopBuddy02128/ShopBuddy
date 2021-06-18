package com.example.shopbuddy.models;

public class Store {
    String name;
    String address;
    String id;

    public Store(String name, String address, String id){
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
}

