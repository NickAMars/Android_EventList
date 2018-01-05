package com.nicholausmarsden.eventlist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by nicho on 12/28/2017.
 */

public class AlarmReceiver  extends BroadcastReceiver {
private Notification.Builder builder;
private NotificationManager notificationManager;
private int notification_id;
private RemoteViews remoteViews;
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getExtras().getString("NAME");
        String desc = intent.getExtras().getString("DESC");
        Toast.makeText(context, "Your alarm is going off!", Toast.LENGTH_SHORT).show();
        mNotification(context,name ,desc ,"Alert"); // callNotification
    }

    public void mNotification(Context context, String title, String descr, String alert) {


        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder buildNotific = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
            //    setSmallIcon(R.drawable.bird).
                setContentTitle(title).
                setTicker(alert).
                setContentText(descr).
                setContentIntent(notificationIntent).
                setDefaults(NotificationCompat.DEFAULT_SOUND).
                setAutoCancel(true);

        NotificationManager NotMnger = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        NotMnger.notify(1, buildNotific.build());

    }


}
