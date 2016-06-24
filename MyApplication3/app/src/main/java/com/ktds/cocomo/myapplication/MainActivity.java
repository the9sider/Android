package com.ktds.cocomo.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvCounter;
    private Button btnStart;
    private Button btnStop;
    private IMyCounterService binder;

    /**
     * Activity가 Service를 호출합니다.
     * 이 때, 꼭 binder가 필요합니다.
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            /**
             * Service가 가지고있는 binder를 전달받는다.
             * 즉, Service에서 구체화한 getCount() 메소드를 받았습니다.
             */
            binder = IMyCounterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Intent intent;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter = (TextView) findViewById(R.id.tvCounter);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, MyCounterService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                running = true;
                new Thread(new GetCountThread()).start();
            }
        });

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
                running = false;
            }
        });
    }

    /**
     *
     */
    private class GetCountThread implements Runnable {

        // binder에서 count가져와서 set시키려면 handler 필요
        private Handler handler = new Handler();

        @Override
        public void run() {

            while (running) {

                if(binder == null) {
                    continue;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tvCounter.setText(binder.getCount() + "");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // 0.5초 텀을 준다.
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
