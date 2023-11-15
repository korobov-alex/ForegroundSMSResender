package com.example.groundapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;



public class MyBroadcastRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent serviceIntent = new Intent(context, MyForegroundService.class);
            context.startForegroundService(serviceIntent);
        }
        if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            // Этот код будет выполнен при получении нового SMS
//            Intent serviceIntent = new Intent(context, MyForegroundService.class);
//            context.startForegroundService(serviceIntent);
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");

                    if (pdus != null && pdus.length > 0) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);

                        String sender = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();

                        // Передаем данные в ForegroundService
                        Intent serviceIntent = new Intent(context, MyForegroundService.class);
                        serviceIntent.putExtra("sender", sender);
                        serviceIntent.putExtra("messageBody", messageBody);
                        context.startForegroundService(serviceIntent);
                    }
                }
        }
    }
}
