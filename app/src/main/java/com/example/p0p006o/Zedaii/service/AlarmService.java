package com.example.p0p006o.Zedaii.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by p0p006o on 22/01/17.
 */

public class AlarmService {

    private Context context;

    public AlarmService(Context context) {
        this.context = context;
    }

    public void setAlarm(String id, long time) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), Integer.parseInt(id), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        //Toast.makeText(context,"Setting alarm for id : " + id + " in : " + String.valueOf((time - System.currentTimeMillis()) / 1000/60) + " mins",Toast.LENGTH_LONG).show();
    }

    public void cancelAlarm(String id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), Integer.parseInt(id), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        //Toast.makeText(context,"Cancel alarm for id : "+ id,Toast.LENGTH_LONG).show();
    }
}
