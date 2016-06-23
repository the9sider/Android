package com.ktds.cocomo.memowithweb.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.ktds.cocomo.memowithweb.db.DBHelper;
import com.ktds.cocomo.memowithweb.vo.MemoVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 206-032 on 2016-06-21.
 */
public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private Map<String, String> param;
    private Context context;
    private DBHelper dbHelper;

    public NetworkTask(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context, "MEMO2", null, DBHelper.DB_VERSION);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {
        param = new HashMap<String, String>();
        param = params[0];

        Log.d("MEMO", "doInBackground code : " + param.get("code"));

        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST",
                "http://10.225.152.191:8080/Memo/" + param.get("code"));

        // 파라미터를 전송한다.
        http.addAllParameters(param);

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

        // 반환값이 있으면 전달한다.
        if(s != null && s.length() > 0) {
            Log.d("MEMO", s);
            Gson gson = new Gson();
            MemoVo memo = gson.fromJson(s, MemoVo.class);

            if( memo.getCode().equals("I") ) {
                this.addMemo(memo);
            } else if (memo.getCode().equals("M")) {
                this.modifyMemo(memo);
            } else if (memo.getCode().equals("D")) {
                this.deleteMemo(memo.getMemoId());
            }

            ((AppCompatActivity)context).setResult(Activity.RESULT_OK, new Intent());
            ((AppCompatActivity)context).finish();
        }
    }

    /**
     * Memo 추가
     * @param memo
     */
    public void addMemo(MemoVo memo) {

        Log.d("MEMO", "Insert Network");

        // DB 연결 안되어있으면 생성
        if(dbHelper == null) {
            dbHelper = new DBHelper(context, "MEMO2", null, DBHelper.DB_VERSION);
        }

        // SQLite Memo 추가
        dbHelper.addMemo(memo);
    }

    /**
     * Memo 수정
     * @param memo
     */
    public void modifyMemo(MemoVo memo) {

        Log.d("MEMO", "Modify Network");

        // DB 연결 안되어있으면 생성
        if(dbHelper == null) {
            dbHelper = new DBHelper(context, "MEMO2", null, DBHelper.DB_VERSION);
        }

        // SQLite Memo 수정
        dbHelper.modifyMemo(memo);
    }

    /**
     * Memo 삭제
     * @param memoId
     */
    public void deleteMemo(String memoId) {

        Log.d("MEMO", "Delete Network");

        // DB 연결 안되어있으면 생성
        if(dbHelper == null) {
            dbHelper = new DBHelper(context, "MEMO2", null, DBHelper.DB_VERSION);
        }

        // SQLite Memo 삭제
        dbHelper.deleteMemo(memoId);
    }
}
