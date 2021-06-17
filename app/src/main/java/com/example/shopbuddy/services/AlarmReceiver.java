package com.example.shopbuddy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.shopbuddy.models.DiscountItem;

import java.util.HashMap;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    private int receivedCalls;
    private int callsToReceive;
    private HashMap<String, List<DiscountItem>> listOfItems;

    @Override
    public void onReceive(Context context, Intent intent) {
        String[] items = new String[]{"havredrik", "øl", "banan", "køkkenrulle", "supermand"};
        AlarmService.setReceivedCalls(0);
        AlarmService.setCallsToReceive(items.length);
        AlarmService.resetListOfItems();

        for(String item : items) {
            new DiscountSearchService(this, item).start();
        }
    }
}
