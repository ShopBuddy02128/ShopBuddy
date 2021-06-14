package com.example.shopbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.mainScreen.LoginScreenActivity;
import com.example.shopbuddy.ui.mainScreen.RegisterScreenActivity;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shopbuddy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastService.setmContext(getApplicationContext());

        if(true) {
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