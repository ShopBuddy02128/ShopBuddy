package com.example.shopbuddy.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AlarmService {
    private static Context mContext;
    private static AlarmManager alarmManager;

    private static int receivedCalls;
    private static int callsToReceive;
    private static HashMap<String, List<DiscountItem>> listOfItems;

    private static NotificationsFragment notificationsFragment;

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

    public static void setReceivedCalls(int receivedCalls) {
        AlarmService.receivedCalls = receivedCalls;
    }

    public static void setCallsToReceive(int callsToReceive) {
        AlarmService.callsToReceive = callsToReceive;
    }

    public static void resetListOfItems() {
        AlarmService.listOfItems = new HashMap<String, List<DiscountItem>>();
    }

    public static void finishRequest(String item, List<DiscountItem> itemsOnDiscount) {
        receivedCalls++;
        listOfItems.put(item, itemsOnDiscount);
        if(receivedCalls == callsToReceive) {
            finishAlarmRequest();
        }
    }

    private static void finishAlarmRequest() {
        String message = "Found discounts for:";
        boolean foundDiscount = false;
        for (Map.Entry<String, List<DiscountItem>> entry : listOfItems.entrySet()) {
            String itemName = entry.getKey();
            List<DiscountItem> listOfDiscounts = entry.getValue();

            if(listOfDiscounts.size() > 0) {
                message += "\n- " + itemName;
                foundDiscount = true;
            }
        }

        if(foundDiscount) {
            createDiscountNotification(message);
        }
    }

    public static void createDiscountNotification(String message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
        Intent ii = new Intent(mContext.getApplicationContext(), NavigationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(message);
        bigText.setBigContentTitle("Discounts found!");
        bigText.setSummaryText("Your discount alarm is going off!");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Discounts found!");
        mBuilder.setContentText("Click to view what you can save!");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationService.showNotification(mBuilder);
    }


    public static NotificationsFragment getNotificationsFragment() {
        return notificationsFragment;
    }

    public static void setNotificationsFragment(NotificationsFragment notificationsFragment) {
        AlarmService.notificationsFragment = notificationsFragment;
    }

    public static void createDiscountAlarm() {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        AlarmService.createAlarm(intent);
    }

    public static void alarmDiscountRemove() {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        AlarmService.removeAlarm(intent);
    }
}
