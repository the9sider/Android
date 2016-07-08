package com.ktds.junho.sems;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LeaveActivity extends AppCompatActivity {

    private Button btnLeave;
    private Button btnNoLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        btnLeave = (Button) findViewById(R.id.btnLeave);
        btnNoLeave = (Button) findViewById(R.id.btnNoLeave);

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveTask leaveTask = new LeaveTask();
                leaveTask.execute("");
            }
        });

        btnNoLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class LeaveTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            /**
             * 로그인 되어 있는지 확인
             * 로그인 되어 않으면 재로그인 시켜줌
             * 퇴실처리
             */

            HttpClient.Builder client = new HttpClient.Builder("POST", "http://192.168.43.142/m/leave");
            client.create().request();

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            LeaveActivity.this.finish();
        }
    }
}
