package com.example.shopbuddy.services;

import com.google.firebase.auth.FirebaseAuth;

public abstract class AuthService {
    private static FirebaseAuth mAuth;

    public static void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

}
