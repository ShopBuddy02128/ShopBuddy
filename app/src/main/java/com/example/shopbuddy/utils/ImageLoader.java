package com.example.shopbuddy.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.services.FoodWasteCallBack;
import com.example.shopbuddy.services.ImageLoadCallBack;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageLoader {
    private int cores = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor = Executors.newFixedThreadPool(cores);
    private AppCompatActivity f;
    private String stringUrl;
    private CircleImageView target;

    public ImageLoader(AppCompatActivity f, String stringUrl){
        this.f = f;
        this.stringUrl = stringUrl;
    }

    public void stop(){executor.shutdown();}

    public void loadImage(ImageLoadCallBack callBack){
        executor.execute(() -> {
            Bitmap data = null;

            Future<Bitmap> futureData = executor.submit(downloadImage());

            try{
                data = futureData.get();
                Log.i("BITMAP","Data retrieved sucesfully");
            }catch(Exception e){
                Log.i("BITMAP","Data not retrieved sucesfully");
                e.printStackTrace();
            }

            final Bitmap finalData = data;

            f.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.onImageLoaded(finalData);
                }
            });

            stop();
        });

    }

    public Callable<Bitmap> downloadImage(){
        return () -> {
            URL url = null;
            try {
                url = new URL(stringUrl);
                Log.i("BITMAP", "URL loaded succesfully");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("BITMAP", "URL not loaded succesfully");
                return null;
            }

            try {

                Bitmap bmp = ImageCache.loadImage(stringUrl);
                if (bmp == null) {
                    Log.i("Bruh","bmp is null");
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.i("BITMAP", "" + bmp);
                    ImageCache.updateCache(stringUrl, bmp);
                }

                return bmp;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        };
    }

}
