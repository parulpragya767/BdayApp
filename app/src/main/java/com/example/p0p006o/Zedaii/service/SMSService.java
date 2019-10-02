package com.example.p0p006o.Zedaii.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;
import com.example.p0p006o.Zedaii.model.Greeting;

/**
 * Created by p0p006o on 22/01/17.
 */

public class SMSService {

    private Context context;

    public SMSService(Context context) {
        this.context = context;
    }

    public void sendSMS(Greeting greeting) {

//        Intent intent=new Intent(context.getApplicationContext(), SMSService.class);
//        PendingIntent pi=PendingIntent.getActivity(context, 0, intent,0);
//
//        SmsManager sms=SmsManager.getDefault();
//        sms.sendTextMessage(greeting.getPhoneNumber(), null, greeting.getMessage(), pi,null);

        Toast.makeText(context, "SMS sent for id : " + greeting.getId(), Toast.LENGTH_LONG).show();
    }
}
