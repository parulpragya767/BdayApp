package com.example.p0p006o.Zedaii.service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p0p006o.Zedaii.R;
import com.example.p0p006o.Zedaii.activity.DisplayGreetingActivity;
import com.example.p0p006o.Zedaii.activity.GreetingActivity;
import com.example.p0p006o.Zedaii.db.DBHelper;
import com.example.p0p006o.Zedaii.model.Greeting;
import com.example.p0p006o.Zedaii.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by p0p006o on 20/01/17.
 */

public class GreetingAdapter extends ArrayAdapter<Greeting> {

    private AlarmService alarmService = new AlarmService(getContext());

    private DBHelper dbHelper;

    public GreetingAdapter(Context context, ArrayList<Greeting> arr, DBHelper dbHelper) {
        super(context, R.layout.greeting_adapter ,arr);
        this.dbHelper = dbHelper;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.greeting_adapter, parent, false);

        final Greeting greeting = getItem(position);
        TextView displayName = (TextView) customView.findViewById(R.id.greetingName);
        displayName.setText(greeting.getDisplay());

        RelativeLayout greetingLayout = (RelativeLayout) customView.findViewById(R.id.greetingLayout);
        greetingLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DisplayGreetingActivity.class);
                intent.putExtra("id", greeting.getId());
                getContext().startActivity(intent);
            }
        });

        ImageButton deleteButton = (ImageButton) customView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dbHelper.delete(greeting.getId());
                alarmService.cancelAlarm(greeting.getId());
                getContext().startActivity(new Intent(getContext(), GreetingActivity.class));
            }
        });

        Switch isActiveSwitch = (Switch) customView.findViewById(R.id.isActive);
        if(!greeting.getActive()){
            isActiveSwitch.setChecked(false);
        }

        isActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,  boolean isChecked) {

                if(isChecked){
                    Calendar calendar = DateUtil.convertStringToDate(greeting.getDate());

                    if(calendar.getTimeInMillis() >  System.currentTimeMillis()) {
                        greeting.setActive(true);
                        dbHelper.update(greeting.getId(), greeting);
                        alarmService.setAlarm(greeting.getId(), calendar.getTimeInMillis());
                    }
                    else {
                        buttonView.setChecked(false);
                        Toast.makeText(getContext(), "Please enter a valid time", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    greeting.setActive(false);
                    dbHelper.update(greeting.getId(), greeting);
                    alarmService.cancelAlarm(greeting.getId());
                }
            }
        });

        return customView;
    }}