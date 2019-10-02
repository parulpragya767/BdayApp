package com.example.p0p006o.Zedaii.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by p0p006o on 24/01/17.
 */

public class DateUtil {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String convertDateToString(Calendar calender){
        return format.format(calender.getTime());
    }

    public static Calendar convertStringToDate(String str) {
        Date date;
        Calendar calender = Calendar.getInstance();
        try {
            date=format.parse(str);
            calender.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calender;
    }


}
