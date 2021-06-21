package com.example.shopbuddy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AuthService.initializeFirebaseAuth();
        NotificationService.setContext(context);
        AlarmService.setmContext(context);
        new FirestoreHandler().getDiscountAlarmListFromAlarmReceiver(AuthService.getCurrentUserId());
    }
}
