package com.ktds.cocomo.simpleboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.cocomo.simpleboard.db.SimpleDB;
import com.ktds.cocomo.simpleboard.vo.ArticleVO;

public class DetailActivity extends AppCompatActivity {

    private TextView tvSubject;
    private TextView tvArticleNumber;
    private TextView tvAuthor;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvArticleNumber = (TextView) findViewById(R.id.tvArticleNumber);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        // MainActivity에서 보낸 intent 받기
        Intent intent = getIntent();

        // key가 String 형식을 왔기 때문에 getStringExtra로 받는다.
        // int형으로 오면 getIntExtra를 이용한다.
        String key = intent.getStringExtra("key");

        // Key 이용해 VO 받아오기
        ArticleVO article = SimpleDB.getArticle(key);

        // 각 TextView에 받아온 VO로 입력하기
        tvSubject.setText(article.getSubject());
        tvArticleNumber.setText(article.getArticleNo() + "");
        tvAuthor.setText(article.getAuthor());
        tvDescription.setText(article.getDescription());
    }

    /**
     * 뒤로가기 버튼 눌렀을 때
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(DetailActivity.this, "액티비티를 종료합니다.", Toast.LENGTH_SHORT).show();
    }
}
