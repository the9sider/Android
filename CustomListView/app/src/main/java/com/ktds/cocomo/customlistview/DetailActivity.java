package com.ktds.cocomo.customlistview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ktds.cocomo.customlistview.facebook.Facebook;
import com.restfb.types.Post;

public class DetailActivity extends ActionBarActivity {

    private Facebook facebook;
    private Handler handler;
    private TextView tvSubject;
    private TextView tvAuthor;
    private TextView tvHitCount;
    private LinearLayout ll;

    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("상세보기");

        handler = new Handler();
        facebook = new Facebook(this);
        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvHitCount = (TextView) findViewById(R.id.tvHitCount);
        ll = (LinearLayout) findViewById(R.id.ll);

        postId = getIntent().getStringExtra("postId");
        if (postId != null && postId.length() > 0) {

            facebook.auth(new Facebook.After() {
                @Override
                public void doAfter(Context context) {
                    // this : 현재 Activity의 context
                    setPost(postId);
                }
            });
        }

        // 뒤로가기 버튼 만들기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void setPost(String postId) {
        facebook.getPostDetail(postId, new Facebook.PostSerializable() {
            @Override
            public void serialize(final Post post) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (post.getMessage() != null) {
                            tvSubject.setText(post.getMessage());
                        } else {
                            tvSubject.setText("메시지 없음");
                        }

                        if (post.getFrom().getName() != null) {
                            tvAuthor.setText(post.getFrom().getName());
                        } else {
                            tvAuthor.setText("작성자 없음");
                        }

                        if (post.getLikes() != null) {
                            tvHitCount.setText(post.getLikes().getData().size() + "");
                        } else {
                            tvHitCount.setText("0");
                        }

                        if (post.getCommentsCount() > 0) {
                            for (int i = 0; i < post.getCommentsCount(); i++) {
                                TextView tvComment = new TextView(DetailActivity.this);
                                tvComment.setText(post.getComments().getData().get(i).getMessage());
                                ll.addView(tvComment);
                            }
                        }
                    }
                });
            }
        });
    }
}
