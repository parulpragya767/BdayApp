package com.example.p0p006o.Zedaii.util;

import android.content.Context;
import android.widget.Toast;

import com.example.p0p006o.Zedaii.model.Greeting;

import java.util.Calendar;

/**
 * Created by p0p006o on 25/01/17.
 */

public class GreetingValidator {

    private Context context;

    public GreetingValidator(Context context) {
        this.context = context;
    }

    public Boolean validate(Greeting greeting) {

        if(greeting.getDisplay() == null || greeting.getDisplay().equals("")) {
            Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_LONG).show();
            return false;
        }

        Calendar calender = DateUtil.convertStringToDate(greeting.getDate());
        if(calender.getTimeInMillis() < System.currentTimeMillis()) {
            Toast.makeText(context, "Please enter a valid time", Toast.LENGTH_LONG).show();
            return false;
        }

        if((greeting.getName() == null || greeting.getName().equals("") ||
                (greeting.getPhoneNumber() == null || greeting.getPhoneNumber().equals("")))) {
            Toast.makeText(context, "Please enter a valid contact", Toast.LENGTH_LONG).show();
            return false;
        }

        if(greeting.getMessage() == null || greeting.getMessage().equals("")) {
            Toast.makeText(context, "Please enter a valid message", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
