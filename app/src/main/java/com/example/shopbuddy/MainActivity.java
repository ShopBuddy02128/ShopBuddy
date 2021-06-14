package com.example.shopbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopbuddy.ui.mainScreen.LoginScreenActivity;
import com.example.shopbuddy.ui.mainScreen.RegisterScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shopbuddy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(true) {
            setContentView(R.layout.startscreen_activity);
            getSupportActionBar().hide();
            setupMainMenuScreen();

        } else {
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_offers, R.id.navigation_shoplist, R.id.navigation_notifications,R.id.navigation_map)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
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