package com.hit.jobjob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class ActivityWelcome extends AppCompatActivity {

    private static int STANDBY_TIME = 5000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(ActivityWelcome.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        }, STANDBY_TIME);
    }
}
