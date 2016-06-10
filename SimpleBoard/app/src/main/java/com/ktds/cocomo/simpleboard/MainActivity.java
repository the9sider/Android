package com.ktds.cocomo.simpleboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ktds.cocomo.simpleboard.db.SimpleDB;
import com.ktds.cocomo.simpleboard.vo.ArticleVO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB에 글 99개 넣기
        prepareSimpleDB();

        // itemList 받아오기
        LinearLayout ll = (LinearLayout) findViewById(R.id.itemList);

        // 반복 시작
        for(int i = 0; i < SimpleDB.getIndexes().size(); i++ ) {
            Button button = new AppCompatButton(this);
            button.setText(SimpleDB.getIndexes().get(i));

            // 해당 글 버튼을 클릭하면 글 번호를 가지고 DetailActivity로 이동동
           button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = (String) ((Button) v).getText();

                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("key", buttonText);
                    startActivity(intent);
                }
            });

            ll.addView(button);
        }
        // 반복 끝

    }

    /**
     * DB에 글 99개 넣기
     */
    private void prepareSimpleDB() {

        for (int i = 1; i < 100; i++) {
            SimpleDB.addArticle(i + "번글", new ArticleVO(i, i + "번글 제목", i + "번글 내용", i + "번글 작성자"));
        }
    }

    private long pressedTime;

    @Override
    public void onBackPressed() {

        // Alert을 이용해 종료시키기
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog  .setTitle("종료 알림")
                .setMessage("정말 종료하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "종료합니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "취소했습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "종료하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }
}
