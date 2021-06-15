package com.example.shopbuddy.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "ImageLoadTask";
    private CircleImageView targetView;

    public ImageLoadTask(CircleImageView targetView) {
        this.targetView = targetView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlString = strings[0];

        URL url = null;
        Log.i(TAG, "Strings = " + Arrays.toString(strings));
        Log.i(TAG, "URL string = " + urlString);
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            assert url != null;

            Bitmap bmp = ImageCache.loadImage(urlString);
            if (bmp == null) {
                Log.i("Bruh","bmp is null");
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ImageCache.updateCache(urlString, bmp);
            }

            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Bitmap result) {
        this.targetView.setImageBitmap(result);
    }
}
