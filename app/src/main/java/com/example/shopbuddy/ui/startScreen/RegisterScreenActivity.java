package com.example.shopbuddy.ui.startScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

public class RegisterScreenActivity extends Activity {
    private final String errorStringMissing = "Error: Password or username missing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Button loginBtn = (Button) findViewById(R.id.registerScreen_registerBtn);
        EditText usernameInput = (EditText) findViewById(R.id.register_username_editText);
        EditText passwordInput = (EditText) findViewById(R.id.register_password_editText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (email.equals("") || password.equals("")) {
                    ToastService.makeToast(errorStringMissing, Toast.LENGTH_SHORT);
                    return;
                }
                registerUser(email, password);
            }
        });
    }

    private void registerUser(String email, String password) {
        AuthService.getmAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    ToastService.makeToast("Successfully created user and logged in", Toast.LENGTH_SHORT);
                    registerSuccessful();
                } else {
                    ToastService.makeToast("Failed to create user!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void registerSuccessful() {
        Intent createNavigationActivity = new Intent(RegisterScreenActivity.this, NavigationActivity.class);
        // The following flags clear the activity stack, meaning you cant go back to the startScreen / loginScreen / registerScreen withouth logging out.
        createNavigationActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(createNavigationActivity);
    }


}
