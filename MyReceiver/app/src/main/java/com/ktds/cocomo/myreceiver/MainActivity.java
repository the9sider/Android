package com.ktds.cocomo.myreceiver;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionRequester.Builder
                requester = new PermissionRequester.Builder(this);
        requester.create().request(
                Manifest.permission.RECEIVE_SMS,
                10000,
                new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {
                        Toast.makeText(MainActivity.this, "권한을 얻지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Intent intent = getIntent();
        String sms = intent.getStringExtra("SMS");
        tvSubject = (TextView) findViewById(R.id.tvSubject);
        Toast.makeText(MainActivity.this, tvSubject.getText().toString(), Toast.LENGTH_SHORT).show();
        if(sms != null) {
            tvSubject.setText(sms);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        String sms = intent.getStringExtra("SMS");
        Toast.makeText(MainActivity.this, tvSubject.getText().toString(), Toast.LENGTH_SHORT).show();
        tvSubject.setText(sms);
    }
}
