package com.example.p0p006o.Zedaii.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.p0p006o.Zedaii.R;
import com.example.p0p006o.Zedaii.db.DBHelper;
import com.example.p0p006o.Zedaii.model.Greeting;
import com.example.p0p006o.Zedaii.service.AlarmService;
import com.example.p0p006o.Zedaii.util.DateUtil;
import com.example.p0p006o.Zedaii.util.GreetingValidator;

import java.util.Calendar;


/**
 * Created by p0p006o on 20/01/17.
 */

public class DisplayGreetingActivity extends BaseActivity {

    private DBHelper dbHelper = new DBHelper(this);

    private AlarmService alarmService = new AlarmService(this);

    private GreetingValidator validator = new GreetingValidator(this);

    private Calendar currentCalendar = Calendar.getInstance();

    private Calendar greetingCalendar = Calendar.getInstance();

    private Boolean sendSMS = true;

    private Boolean isActive = true;

    private Boolean isUpdate = false;

    private String idToUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_greeting_activity);

        String id = getIntent().getStringExtra("id");
        if(id !=null) {
            isUpdate = true;
            idToUpdate = id;

            EditText displayName = (EditText) findViewById(R.id.displayEditText);
            TextView contactName = (TextView) findViewById(R.id.nameTextView);
            TextView phoneNumber = (TextView) findViewById(R.id.phoneNumberTextView);
            EditText message = (EditText) findViewById(R.id.messageEditText);
            CheckBox sendSMS = (CheckBox) findViewById(R.id.sendSMSCheckBox);

            Greeting greeting = dbHelper.get(idToUpdate);
            displayName.setText(greeting.getDisplay());
            contactName.setText(greeting.getName());
            phoneNumber.setText(greeting.getPhoneNumber());
            message.setText(greeting.getMessage());
            sendSMS.setChecked(greeting.getSendSMS());
            isActive = greeting.getActive();
            currentCalendar = DateUtil.convertStringToDate(greeting.getDate());
            greetingCalendar = DateUtil.convertStringToDate(greeting.getDate());
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,GreetingActivity.class));
        this.finish();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day) {
                    greetingCalendar.set(year, month, day);
                }
            };

    public void onDateButtonClick(View view){

        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, R.style.DialogTheme, myDateListener, year, month, day);
        datePickerDialog.show();
    }

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            greetingCalendar.set(Calendar.HOUR_OF_DAY, hour);
            greetingCalendar.set(Calendar.MINUTE, min);
        }
    };

    public void onTimeButtonClick(View view){
        int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int min = currentCalendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, R.style.DialogTheme,  myTimeListener, hour, min, false
        );
        timePickerDialog.show();
    }

    public void onContactButtonClick(View view) {

//        TextView contactNameText = (TextView) findViewById(R.id.nameTextView);
//        TextView phoneNumberText = (TextView) findViewById(R.id.phoneNumberTextView);
//        contactNameText.setText("Pramit");
//        phoneNumberText.setText("9980716504");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String name = c.getString(0);
                        String number = c.getString(1);

                        TextView contactNameText = (TextView) findViewById(R.id.nameTextView);
                        TextView phoneNumberText = (TextView) findViewById(R.id.phoneNumberTextView);
                        contactNameText.setText(name);
                        phoneNumberText.setText(number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if(checked) {
            sendSMS = true;
        } else {
            sendSMS = false;
        }
    }

    public void onSaveButtonClick(View view){
        String id;
        EditText displayName = (EditText) findViewById(R.id.displayEditText);
        TextView contactName = (TextView) findViewById(R.id.nameTextView);
        TextView phoneNumber = (TextView) findViewById(R.id.phoneNumberTextView);
        EditText message = (EditText) findViewById(R.id.messageEditText);
        String reminderMessage = "Its " + contactName.getText().toString() + "'s Birthday!!\n Send a message :) :)";
        String date = DateUtil.convertDateToString(greetingCalendar);

        Greeting greeting = new Greeting();
        greeting.setDisplay(displayName.getText().toString());
        greeting.setName(contactName.getText().toString());
        greeting.setPhoneNumber(phoneNumber.getText().toString());
        greeting.setDate(date);
        greeting.setMessage(message.getText().toString());
        greeting.setSendSMS(sendSMS);
        greeting.setActive(isActive);
        greeting.setReminderMessage(reminderMessage);

        Boolean isValid = validator.validate(greeting);

        if(isValid) {
            if(isUpdate) {
                id = dbHelper.update(idToUpdate, greeting);

                if(!date.equalsIgnoreCase(DateUtil.convertDateToString(currentCalendar)) && isActive) {
                    alarmService.cancelAlarm(id);
                    alarmService.setAlarm(id, greetingCalendar.getTimeInMillis());
                }
            }
            else {
                id = dbHelper.save(greeting);
                alarmService.setAlarm(id, greetingCalendar.getTimeInMillis());
            }

            startActivity(new Intent(this,GreetingActivity.class));
            this.finish();
        }
    }
}
