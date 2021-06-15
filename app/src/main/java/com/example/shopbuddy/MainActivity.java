package com.example.shopbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.ui.startScreen.LoginScreenActivity;
import com.example.shopbuddy.ui.startScreen.RegisterScreenActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    public static final String TAG = "MainActivity";
    
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastService.setmContext(getApplicationContext());
        AuthService.initializeFirebase();

        if(!AuthService.isLoggedIn()) {
            setContentView(R.layout.startscreen_activity);
            getSupportActionBar().hide();
            setupMainMenuScreen();

        } else {
            Intent createNavigationActivity = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(createNavigationActivity);
        }
    }

    private void setupMainMenuScreen() {
        Button loginBtn = (Button) findViewById(R.id.startScreen_loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLoginScreen = new Intent(MainActivity.this, LoginScreenActivity.class);
                startActivity(openLoginScreen);
            }
        });

        TextView registerBtn = (TextView) findViewById(R.id.mainScreen_registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRegisterScreen = new Intent(MainActivity.this, RegisterScreenActivity.class);
                startActivity(openRegisterScreen);
            }
        });
    }
}