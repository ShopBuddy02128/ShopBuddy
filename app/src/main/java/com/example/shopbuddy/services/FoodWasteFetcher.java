package com.example.shopbuddy.services;

import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.ui.foodwaste.FoodWasteFragment;
import com.example.shopbuddy.utils.JSONReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FoodWasteFetcher {
    private int cores = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor = Executors.newFixedThreadPool(cores);
    private FoodWasteFragment f;
    private String zipCode;

    public FoodWasteFetcher(FoodWasteFragment f, String zipCode){
        this.f = f;
        this.zipCode = zipCode;
    }

    public void stop(){
        executor.shutdown();
    }

    public void getData(ResultCallBack callBack){
        executor.execute(() -> {

            ArrayList<FoodWasteFromStore> data = null;

            Future<ArrayList<FoodWasteFromStore>> futureData = executor.submit(retrieveData());

            try{
                data = futureData.get();
            }catch(Exception e){
                e.printStackTrace();
            }

            final ArrayList<FoodWasteFromStore> finalData = data;

            f.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.onResult(finalData);
                }
            });

            stop();

        });
    }

    public Callable<ArrayList<FoodWasteFromStore>> retrieveData(){
        return () -> {
            Request request = new Request.Builder()
                    .url("https://api.sallinggroup.com/v1/food-waste/?zip=" + zipCode)
                    .header("Authorization", "Bearer dc92d87d-1f6d-4fcc-8a66-ddc95c5d9a24")
                    .build();

            try (Response response = new OkHttpClient().newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                String resultString = response.body().string();
                ArrayList<FoodWasteFromStore> result = JSONReader.getFoodWasteFromJson(resultString);

                return result;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return null;
        };
    }
}
