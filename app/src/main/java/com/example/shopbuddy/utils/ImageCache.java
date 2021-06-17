package com.example.shopbuddy.utils;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// cache images downloaded from web
public abstract class ImageCache {
    static final int CACHE_SIZE = 50;
    static Queue<String> currentlyLoadedUrls = new LinkedList<>();

    // <ImageUrl, Image> -> maps urlString to the loaded image
    static HashMap<String, Bitmap> images = new HashMap<>();

    public static Bitmap loadImage(String urlString) {
        Log.i("ImageCache", "Entered loadImage");
        return images.get(urlString);
    }

    public static void updateCache(String urlString, Bitmap bmp) {
        Log.i("ImageCache", "Entered updateCache");
        if (currentlyLoadedUrls.size() >= CACHE_SIZE) {
            String toDelete = currentlyLoadedUrls.poll();
            images.remove(toDelete);
        }

        images.put(urlString, bmp);
        currentlyLoadedUrls.add(urlString);
    }
}
