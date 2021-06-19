package com.example.shopbuddy.services;

import com.google.firebase.auth.FirebaseAuth;

public abstract class AuthService {
    private static FirebaseAuth mAuth;

    public static String getCurrentUserId() {
        if (isLoggedIn())
            return mAuth.getUid();
        else
            return "OOvklbbUonTliRCOsZRO3EPnBN53";
    }

    public static void initializeFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }
}
