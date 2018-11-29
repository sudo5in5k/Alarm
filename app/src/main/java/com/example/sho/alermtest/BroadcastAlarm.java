package com.example.sho.alermtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * TODO クラス説明
 *
 * Created by sho on 2017/08/31.
 */

public class BroadcastAlarm extends BroadcastReceiver {
    public static final String TAG = "DEBUG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Received!", Toast.LENGTH_SHORT).show();
        setNotification(context, intent);
        setSound(context);
    }

    public void setSound(Context context){
        MediaPlayer mp = MediaPlayer.create(context, R.raw.chime);
        mp.start();
    }

    public void setNotification(Context context, Intent intent){
        Intent sendIntent = new Intent(context, MainActivity.class);
        PendingIntent sender = PendingIntent.getActivity(context, 0, sendIntent, 0);

        // 通知オブジェクトの生成
        Notification noti = new NotificationCompat.Builder(context)
                .setContentTitle("通知")
                .setContentText("設定した時間がきました")
                .setSmallIcon(R.drawable.droid_kun)
                .setVibrate(new long[]{0, 200, 100, 200, 100, 200})
                .setAutoCancel(true)
                .setContentIntent(sender)
                .build();

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, noti);
    }
}
