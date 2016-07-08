package com.ktds.junho.sems;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MinChang Jang on 2016-06-23.
 */
public class MyApplication extends Application {

    // 여러개의 비콘 관리하기
    private List<BeaconManager> beaconManagers;

    private BeaconManager beaconManager;


    /**
     * Application을 설치할 때 실행됨.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());

        // Application 설치가 끝나면 Beacon Monitoring Service를 시작한다.
        // Application을 종료하더라도 Service가 계속 실행된다.
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"), // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.
                        0, 0));
            }
        });

        // Android 단말이 Beacon 의 송신 범위에 들어가거나, 나왔을 때를 체크한다.
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                //  showNotification("들어옴", "비콘 연결됨" + list.get(0).getRssi());
                String id = "test02";
                GetEducationInfoTask getEducationInfoTask = new GetEducationInfoTask();
                getEducationInfoTask.execute("");
            }

            @Override
            public void onExitedRegion(Region region) {
                // showNotification("나감", "비콘 연결끊김");
                Intent intent = new Intent(getApplicationContext(), LeaveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        beaconManagers = new ArrayList<>();
        beaconManagers.add(beaconManager);
    }

    /**
     * Notification으로 Beacon 의 신호가 연결되거나 끊겼음을 알림.
     *
     * @param title
     * @param message
     */
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private class GetEducationInfoTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            // 1. 현재 시간에 강의하는 과목에 출결 이력이 있는지 확인한다.
            HttpClient.Builder client = new HttpClient.Builder("POST", "http://192.168.43.142/m/checkAttend");
            client.addOrReplaceParameter("id", params[0]);

            HttpClient post = client.create();
            post.request();

            return post.getBody();
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("RESULT-APPLICATION", s);

            // 2. 출결 이력이 없을 경우 로그인을 할 수 있도록 Activity를 띄워준다.

            if( s.equalsIgnoreCase("false")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("id", "test02");
                intent.putExtra("password", "123qwe!@#qwe");

                getApplicationContext().startActivity(intent);
            }

        }
    }
}