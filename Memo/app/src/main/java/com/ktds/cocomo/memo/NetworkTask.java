package com.ktds.cocomo.memo;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by 206-032 on 2016-06-21.
 */
public class NetworkTask extends AsyncTask<Map<String, String>, int, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {

        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST",
                "http://10.225.152.191:8080/Memo/android");

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();

        // 응답 본문 가져오기
        String body = post.getBody();

        return body;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
