package com.ktds.cocomo.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MyCounterService extends Service {

    private boolean isStop;
    private int count;
    private TextToSpeech tts;

    public MyCounterService() {
    }

    /**
     * Service, Activity 통신을 위한 Binder 객체
     * Activity에게 getCount() 메소드를 제공해 Service의 count 값을 전달한다.
     */
    IMyCounterService.Stub binder = new IMyCounterService.Stub() {
        @Override
        public int getCount() throws RemoteException {
            return count;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

        // Thread를 이용해 Counter 실행시키기
        Thread counter = new Thread(new Counter());
        counter.start();
    }

    /**
     * StopService가 실행될 때 호출된다.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isStop = true;
        return super.onUnbind(intent);
    }

    private class Counter implements Runnable {


        private Handler handler = new Handler();

        @Override
        public void run() {

            /**
             * 10초로 설정
             */
            for (count = 0; count < 10; count++) {

                // STOP 버튼을 눌렀다면 종료한다.
                if (isStop) {
                    break;
                }


                /**
                 * Thread 안에서는 UI와 관련된 Toast를 쓸 수 없습니다.
                 * 따라서, Handler를 통해 이용할 수 있도록 만들어줍니다.
                 */
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        // Toast로 Count 띄우기
                        Toast.makeText(getApplicationContext()
                                , count + ""
                                , Toast.LENGTH_SHORT).show();

                        // Log로 Count 찍어보기
                        Log.d("COUNT", count + "");

                    }


                });

                // Sleep을 통해 1초씩 쉬도록 한다.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "서비스가 종료되었습니다.", Toast.LENGTH_SHORT).show();
                    tts.speak("라면이 다 익었습니다.", TextToSpeech.QUEUE_ADD, null);
                }
            });
        }
    }
}
