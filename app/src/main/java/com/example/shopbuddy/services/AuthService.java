package com.example.shopbuddy.services;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public abstract class AuthService {
    private static FirebaseAuth mAuth;

    public static void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }
}
