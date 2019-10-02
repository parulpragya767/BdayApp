package com.example.p0p006o.Zedaii.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.p0p006o.Zedaii.model.Greeting;

import java.util.ArrayList;

/**
 * Created by p0p006o on 20/01/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GreetingApp.db";

    public static final String TABLE_NAME = "greetings1";

    public static final String ID_COLUMN_NAME = "id";

    public static final String DISPLAY_COLUMN_NAME = "display";

    public static final String NAME_COLUMN_NAME = "name";

    public static final String PHONE_NUMBER_COLUMN_NAME = "phoneNumber";

    public static final String DATE_COLUMN_NAME = "date";

    public static final String MESSAGE_COLUMN_NAME = "message";

    public static final String SEND_SMS_COLUMN_NAME = "sendSMS";

    public static final String IS_ACTIVE_COLUMN_NAME = "isActive";

    public static final String REMINDER_MESSAGE_COLUMN_NAME = "reminderMessage";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + TABLE_NAME  + " (" +
                        ID_COLUMN_NAME + " integer primary key," +
                        DISPLAY_COLUMN_NAME + " text," +
                        NAME_COLUMN_NAME + " text," +
                        PHONE_NUMBER_COLUMN_NAME + " text," +
                        DATE_COLUMN_NAME + " text," +
                        MESSAGE_COLUMN_NAME + " text," +
                        SEND_SMS_COLUMN_NAME + " text," +
                        IS_ACTIVE_COLUMN_NAME + " text," +
                        REMINDER_MESSAGE_COLUMN_NAME + " text" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String save (Greeting greeting) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContent(greeting);
        long id =  db.insert(TABLE_NAME, null, contentValues);
        return String.valueOf(id);
    }

    public Greeting get(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME + " where " + ID_COLUMN_NAME + "="+id+"", null );
        res.moveToFirst();

        Greeting greeting = getGreeting(res);
        return greeting;
    }

    public ArrayList<Greeting> getAll() {
        ArrayList<Greeting> greetings = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Greeting greeting = getGreeting(res);
            greetings.add(greeting);
            res.moveToNext();
        }
        return greetings;
    }

    public String update (String id, Greeting greeting) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContent(greeting);
        db.update(TABLE_NAME, contentValues, ID_COLUMN_NAME + "= ? ", new String[] { id } );
        return id;
    }

    public Integer delete (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ID_COLUMN_NAME + "= ? ", new String[] { id });
    }

    private ContentValues getContent(Greeting greeting) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISPLAY_COLUMN_NAME, greeting.getDisplay());
        contentValues.put(NAME_COLUMN_NAME, greeting.getName());
        contentValues.put(PHONE_NUMBER_COLUMN_NAME, greeting.getPhoneNumber());
        contentValues.put(DATE_COLUMN_NAME, greeting.getDate());
        contentValues.put(MESSAGE_COLUMN_NAME, greeting.getMessage());
        contentValues.put(SEND_SMS_COLUMN_NAME, greeting.getSendSMS().toString());
        contentValues.put(IS_ACTIVE_COLUMN_NAME, greeting.getActive().toString());
        contentValues.put(REMINDER_MESSAGE_COLUMN_NAME, greeting.getReminderMessage());

        return contentValues;
    }

    private Greeting getGreeting(Cursor res) {
        Greeting greeting = new Greeting();

        greeting.setId(res.getString(res.getColumnIndex(DBHelper.ID_COLUMN_NAME)));
        greeting.setDisplay(res.getString(res.getColumnIndex(DBHelper.DISPLAY_COLUMN_NAME)));
        greeting.setName(res.getString(res.getColumnIndex(DBHelper.NAME_COLUMN_NAME)));
        greeting.setPhoneNumber(res.getString(res.getColumnIndex(DBHelper.PHONE_NUMBER_COLUMN_NAME)));
        greeting.setDate(res.getString(res.getColumnIndex(DBHelper.DATE_COLUMN_NAME)));
        greeting.setMessage(res.getString(res.getColumnIndex(DBHelper.MESSAGE_COLUMN_NAME)));
        greeting.setSendSMS(Boolean.parseBoolean(res.getString(res.getColumnIndex(DBHelper.SEND_SMS_COLUMN_NAME))));
        greeting.setActive(Boolean.parseBoolean(res.getString(res.getColumnIndex(DBHelper.IS_ACTIVE_COLUMN_NAME))));
        greeting.setReminderMessage(res.getString(res.getColumnIndex(DBHelper.REMINDER_MESSAGE_COLUMN_NAME)));

        return greeting;
    }
}
