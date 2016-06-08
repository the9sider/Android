package com.ktds.cocomo.addressbook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String model;
    private TextView modelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = Build.MODEL;

        TimerTask myTimer = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, com.ktds.cocomo.addressbook.ListActivity.class);
                startActivity(intent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(myTimer, 2000);

        modelView = (TextView) findViewById(R.id.modelView);
        modelView.setText(model);
    }
}
