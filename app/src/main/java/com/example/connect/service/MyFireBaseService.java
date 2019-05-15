package com.example.connect.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.connect.R;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import androidx.core.app.NotificationCompat;

public class MyFireBaseService extends FirebaseMessagingService {

    final  String NOTIFICATION_CHANNEL_ID ="com.example.connect.service.test";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("MyFireBaseService",remoteMessage.toString());

        if(remoteMessage.getData().isEmpty()){
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        }else {
            showNotificationRemote(remoteMessage.getData());
        }
          }

    private void showNotificationRemote(Map<String, String> data) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String title = data.get("title").toString();
        String body = data.get("body").toString();
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"notification",NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Conneting");
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);


        }
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);

        notifBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO");

        notificationManager.notify(new Random().nextInt(),notifBuilder.build());



    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"notification",NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Conneting");
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);


        }
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);

        notifBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO");

        notificationManager.notify(new Random().nextInt(),notifBuilder.build());







    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKENNOTIFICATION",s);
    }
}
