package com.example.shopbuddy.utils;


import java.util.LinkedList;

public class CustomBackStack {

    private LinkedList<Integer> backStack;

    public CustomBackStack(){
        backStack = new LinkedList<>();
    }

    public void addToBackStack(int num){
        backStack.push(num);
    }

    public CustomBackStack popCurrent(){
        backStack.pop();
        return this;
    }

    public int getCurrent(){
        return size() > 0 ? backStack.getFirst() : 0;
    }

    public int size(){
        return backStack.size();
    }
}
