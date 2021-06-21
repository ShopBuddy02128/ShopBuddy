package com.example.shopbuddy.utils;

public abstract class TextFormatter {
    public static String toNameFormat(String s) {
        String[] strings = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strings.length; i++)
            sb.append(capitalizeFirstLetter(strings[i] + (i != strings.length-1 ? " " : "")));

        return sb.toString();
    }

    public static String capitalizeFirstLetter(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
