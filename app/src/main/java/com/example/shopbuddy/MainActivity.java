package com.example.shopbuddy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.services.AlarmReceiver;
import com.example.shopbuddy.services.AlarmService;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.NotificationService;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.ui.startScreen.LoginScreenActivity;
import com.example.shopbuddy.ui.startScreen.RegisterScreenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastService.setmContext(getApplicationContext());
        NotificationService.setContext(getApplicationContext());
        AlarmService.setmContext(getApplicationContext());
        AuthService.initializeFirebase();

        if(AuthService.isLoggedIn()) {
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
                //alarmCreator();


                Intent openLoginScreen = new Intent(MainActivity.this, LoginScreenActivity.class);
                startActivity(openLoginScreen);
            }
        });

        TextView registerBtn = (TextView) findViewById(R.id.mainScreen_registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createAlarm();

                Intent openRegisterScreen = new Intent(MainActivity.this, RegisterScreenActivity.class);
                startActivity(openRegisterScreen);
            }
        });
    }

    public void addNotif() {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), RegisterScreenActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("YOUOUOUO");
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

//      === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }

    public void alarmCreator() {
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        if(AlarmService.alarmExists(intent)) {
            System.out.println("Alarm Exists");
        } else {
            System.out.println("Alarm Doesnt exist");
        }
    }

    public void createAlarm() {
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        AlarmService.createAlarm(intent);
    }
}