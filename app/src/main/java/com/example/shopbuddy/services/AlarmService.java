package com.example.shopbuddy.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.shopbuddy.MainActivity;

import java.util.Calendar;

public abstract class AlarmService {
    private static Context mContext;
    private static AlarmManager alarmManager;
    public static void setmContext(Context mContext) {
        AlarmService.mContext = mContext;
        AlarmService.alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public static void createAlarm(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 20);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                30*1000,
                pendingIntent);
        System.out.println("Alarm Created");
    }

    public static boolean alarmExists(Intent intent){
        return (PendingIntent.getBroadcast(mContext, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
    }

    public static void removeAlarm(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
                intent, PendingIntent.FLAG_NO_CREATE);
        alarmManager.cancel(pendingIntent);
    }
}
