package com.example.p0p006o.Zedaii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.p0p006o.Zedaii.R;
import com.example.p0p006o.Zedaii.db.DBHelper;
import com.example.p0p006o.Zedaii.model.Greeting;
import com.example.p0p006o.Zedaii.service.GreetingAdapter;

import java.util.ArrayList;

/**
 * Created by p0p006o on 20/01/17.
 */

public class GreetingActivity extends BaseActivity {

    private DBHelper dbHelper = new DBHelper(this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.greeting_activity);

        fetchList();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
        this.finish();
    }

    public void fetchList() {
        ArrayList<Greeting> array_list = dbHelper.getAll();
        ArrayAdapter arrayAdapter=new GreetingAdapter(this, array_list, dbHelper);

        ListView obj = (ListView)findViewById(R.id.listGreetings);
        obj.setAdapter(arrayAdapter);
    }

    public void onSave(View view){
        Intent greeting = new Intent(GreetingActivity.this, DisplayGreetingActivity.class);
        startActivity(greeting);
    }
}
