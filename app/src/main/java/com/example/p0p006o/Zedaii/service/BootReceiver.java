package com.example.p0p006o.Zedaii.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.p0p006o.Zedaii.db.DBHelper;
import com.example.p0p006o.Zedaii.model.Greeting;
import com.example.p0p006o.Zedaii.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by p0p006o on 24/01/17.
 */


public class BootReceiver extends BroadcastReceiver {

    private AlarmService alarmService;

    private DBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {

        alarmService = new AlarmService(context);
        dbHelper = new DBHelper(context);

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            Toast.makeText(context, "Inside Boot Receiver...", Toast.LENGTH_LONG).show();

            ArrayList<Greeting> greetings = dbHelper.getAll();

            for(Greeting greeting : greetings) {
                if(greeting.getActive()) {
                    Calendar calendar = DateUtil.convertStringToDate(greeting.getDate());
                    if(calendar.getTimeInMillis() > System.currentTimeMillis()) {
                        alarmService.setAlarm(greeting.getId(), calendar.getTimeInMillis());
                    }
                }
            }
        }
    }
}
