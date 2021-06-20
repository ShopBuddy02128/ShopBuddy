package com.example.shopbuddy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.shopbuddy.models.DiscountItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    private int receivedCalls;
    private int callsToReceive;
    private HashMap<String, List<DiscountItem>> listOfItems;

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<String> itemsList = AlarmService.getNotificationsFragment().getItems();
        if(itemsList == null) return;
        String[] items = itemsList.toArray(new String[0]);

        AlarmService.setReceivedCalls(0);
        AlarmService.setCallsToReceive(items.length);
        AlarmService.resetListOfItems();

        AlarmService.createDiscountNotification("TESTING MESSAGE");

        for(String item : items) {
            new DiscountSearchService(item).start();
        }
    }
}
