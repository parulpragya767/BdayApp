package com.example.p0p006o.Zedaii.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.p0p006o.Zedaii.R;

/**
 * Created by p0p006o on 25/01/17.
 */

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home_activity);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
