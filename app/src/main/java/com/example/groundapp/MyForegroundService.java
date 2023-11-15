package com.example.groundapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import androidx.annotation.Nullable;


public class MyForegroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String sender = intent.getStringExtra("sender");
            String messageBody = intent.getStringExtra("messageBody");
            EmailService.sendEmail(messageBody, "alexkorobov95@gmail.com", sender);
        }

        final String CHANNEL_ID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Service is running on the foreground")
                .setContentTitle("Incoming SMS resending...")
                .setSmallIcon(R.drawable.ic_launcher_background);

        startForeground(1001, notification.build());



        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
