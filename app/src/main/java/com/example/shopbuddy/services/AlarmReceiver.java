package com.example.shopbuddy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<String> itemsList = AlarmService.getNotificationsFragment().getAlarmItemArrayList();
        if(itemsList == null) return;
        String[] items = itemsList.toArray(new String[0]);

        AlarmService.setReceivedCalls(0);
        AlarmService.setCallsToReceive(items.length);
        AlarmService.resetListOfItems();

        for(String item : items) {
            new DiscountSearchService(item).start();
        }
    }
}
