package com.example.p0p006o.Zedaii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.p0p006o.Zedaii.R;


/**
 * Created by p0p006o on 24/01/17.
 */

public class ReminderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_activity);

        String reminderMessage = getIntent().getStringExtra("reminderMessage");
        if(reminderMessage !=null) {
            TextView reminder = (TextView) findViewById(R.id.reminderTextView);
            reminder.setText(reminderMessage);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    public void onCloseButtonClick(View view) {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }
}
