package com.example.p0p006o.Zedaii.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.p0p006o.Zedaii.R;
import com.example.p0p006o.Zedaii.activity.ReminderActivity;
import com.example.p0p006o.Zedaii.db.DBHelper;
import com.example.p0p006o.Zedaii.model.Greeting;

/**
 * Created by p0p006o on 22/01/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mp;

    private DBHelper dbHelper;

    private SMSService smsService;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelper = new DBHelper(context);

        String id = intent.getStringExtra("id");
        Greeting greeting = dbHelper.get(id);

        if(greeting.getSendSMS()) {
            smsService = new SMSService(context);
            smsService.sendSMS(greeting);
        }

        Intent reminderIntent = new Intent(context, ReminderActivity.class);
        reminderIntent.putExtra("reminderMessage", greeting.getReminderMessage());
        reminderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(reminderIntent);

        mp=MediaPlayer.create(context, R.raw.alrm);
        mp.start();

        greeting.setActive(false);
        dbHelper.update(id, greeting);

        //Toast.makeText(context, "Reminder for id : " + id, Toast.LENGTH_LONG).show();
    }
}