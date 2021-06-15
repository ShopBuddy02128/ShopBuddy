package com.example.shopbuddy.ui.startScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbuddy.R;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

public class LoginScreenActivity extends Activity {
    private final String errorStringMissing = "Error: Password or username missing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button loginBtn = (Button) findViewById(R.id.loginScreen_loginBtn);
        EditText usernameInput = (EditText) findViewById(R.id.login_username_editText);
        EditText passwordInput = (EditText) findViewById(R.id.login_password_editText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (email.equals("") || password.equals("")) {
                    ToastService.makeToast(errorStringMissing, Toast.LENGTH_SHORT);
                    return;
                }
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        AuthService.getmAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        ToastService.makeToast("Successfully logged in", Toast.LENGTH_SHORT);
                        loginSuccessful();
                    } else {
                        ToastService.makeToast("Failed to log in!", Toast.LENGTH_SHORT);
                    }
                }
            });
    }

    private void loginSuccessful() {


        Intent createNavigationActivity = new Intent(LoginScreenActivity.this, NavigationActivity.class);
        // The following flags clear the activity stack, meaning you cant go back to the startScreen / loginScreen / registerScreen withouth logging out.
        createNavigationActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(createNavigationActivity);
    }

}
