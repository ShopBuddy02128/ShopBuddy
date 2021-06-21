package com.example.shopbuddy.utils;


import androidx.fragment.app.Fragment;

import java.util.LinkedList;

public class CustomBackStack {

    private LinkedList<Integer> idStack;

    public CustomBackStack(){

        idStack = new LinkedList<>();
    }

    public void addToBackStack(int num){
        idStack.push(num);
    }

    public CustomBackStack popCurrent(){
        idStack.pop();
        return this;
    }

    public int getCurrent(){
        return size() > 0 ? idStack.getFirst() : 0;
    }

    public int size(){
        return idStack.size();
    }

    public LinkedList<Integer> getRawId(){ return idStack; }
}
