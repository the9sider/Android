package com.ktds.cocomo.myremoteweb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {

    private TextView tvTitle;
    private TextView tvMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMemo = (TextView) findViewById(R.id.tvMemo);


//        NetworkTask networkTask = new NetworkTask();
        NetworkTask2 networkTask = new NetworkTask2();

        Map<String, String> params = new HashMap<String, String>();
        params.put("title", "안드로이드에서 보낸 제목");
        params.put("memo", "안드로이드에서 보낸 메모");

        networkTask.execute(params);
    }

    /**
     * Parameter type of doInBackground :Map<String, String>
     * Progress : Integer
     * return type of doInBackground : String
     */
    public class NetworkTask2 extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * ... 은 가변 배열 또는 가변 파라미터라고 부른다.
         * a, b, c 이런식으로 보내도 되고 배열로 보내도 된다.
         * @param maps
         * @return
         */
        @Override
        protected String doInBackground(Map<String, String>... maps) {

            // HTTP 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST",
                    "http://10.225.152.191:8080/SpringToAndroid/android3");

            // 파라미터를 전송한다.
            http.addAllParameters(maps[0]);

            // HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        /**
         * @param s : doInBackground에서 리턴한 body
         */
        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT", s);

            // json으로 받은 데이터를 data object로 바꿔준다.
            Gson gson = new Gson();
            Data data = gson.fromJson(s, Data.class);
            Log.d("JSON_RESULT", data.getData1());
            Log.d("JSON_RESULT", data.getData2());

            tvTitle.setText(data.getData1());
            tvMemo.setText(data.getData2());
        }
    }

    public class NetworkTask extends AsyncTask<String, String, String> {

        /**
         * doInBackground 실행되기 이전에 동작한다.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 본 작업을 쓰레드로 처리해준다.
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {

            // HTTP 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST",
                    "http://10.225.152.191:8080/SpringToAndroid/android2");

            // 파라미터를 전송한다.
            http.addOrReplace("test", "한글한글");

            // HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        /**
         * doInBackground 종료되면 동작한다.
         * @param s : doInBackground가 리턴한 값이 들어온다.
         */
        @Override
        protected void onPostExecute(String s) {
            Log.d("HTTP_RESULT", s);
        }
    }
}
