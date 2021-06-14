package com.example.shopbuddy.services;

import android.content.Context;
import android.widget.Toast;

public abstract class ToastService {
    static private Context mContext;

    public static void setmContext(Context mContext) {
        ToastService.mContext = mContext;
    }

    public static void makeToast(String toastText, int Toastlength) {
        Toast.makeText(mContext, toastText, Toastlength).show();
    }

    private static void makeToast(String toastText) {
        makeToast(toastText, Toast.LENGTH_SHORT);
    }

}
