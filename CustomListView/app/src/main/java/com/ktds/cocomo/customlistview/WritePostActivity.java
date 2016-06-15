package com.ktds.cocomo.customlistview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ktds.cocomo.customlistview.facebook.Facebook;

public class WritePostActivity extends ActionBarActivity {

    private EditText etPost;
    private Facebook myFacebook;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        myFacebook = new Facebook(this);
        handler = new Handler();

        setTitle("새 글 등록");

        // 뒤로가기 버튼 만들기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        etPost = (EditText) findViewById(R.id.etPost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if( itemId == R.id.done) {

            // Validation Check
            if( etPost.getText().toString().length() == 0) {
                // 글자를 입력하라는 메시지를 띄운다.
                Toast.makeText(WritePostActivity.this, "포스트 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                return false;
            }
            // EditText Post 작성란에 글이 작성되어 있으면 포스트 전송
            else {
                // Facebook으로 포스트 전송
                myFacebook.auth(new Facebook.After() {
                    @Override
                    public void doAfter(Context context) {
                        writePost();
                    }
                });
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void writePost() {
        myFacebook.publishing(etPost.getText().toString(), new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }
}
