package com.example.shopbuddy.utils;

public abstract class TextFormatter {
    public static String toNameFormat(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
