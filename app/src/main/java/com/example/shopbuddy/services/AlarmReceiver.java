package com.example.shopbuddy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastService.makeToast("ALARM MESSAGE BIGGIE BIGGIREBI", Toast.LENGTH_SHORT);
    }
}
