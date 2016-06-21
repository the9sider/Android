package com.ktds.cocomo.memowithweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ktds.cocomo.memowithweb.utility.NetworkTask;
import com.ktds.cocomo.memowithweb.vo.MemoVo;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    // 선언
    private EditText etTitle;
    private EditText etDescription;
    private NetworkTask networkTask;
    private Map<String, String> param;
    private MemoVo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 초기화
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        networkTask = new NetworkTask(DetailActivity.this);
        param = new HashMap<String, String>();
        memo = new MemoVo();
        memo = (MemoVo) getIntent().getSerializableExtra("memo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( etTitle.getText().length() > 0 && etDescription.getText().length() > 0 ) {
                    Log.d("MEMO", "Delete DetailActivity");
                    param.put("code", "deleteMemo");
                    param.put("memoId", memo.getMemoId());
                    param.put("title", memo.getTitle());
                    param.put("description", memo.getDescription());
                    networkTask.execute(param);
                } else if ( etTitle.getText().length() == 0 ) {
                    Toast.makeText(DetailActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if ( etDescription.getText().length() == 0 ) {
                    Toast.makeText(DetailActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 받은 메모가 있으면 EditText에 입력
        fab.setVisibility(View.INVISIBLE);
        if(memo != null && memo.getMemoId() != null) {
            fab.setVisibility(View.VISIBLE);
            etTitle.setText(memo.getTitle());
            etDescription.setText(memo.getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_complete) {

            // 제목 입력했는지 확인
            if( etTitle.getText() == null || etTitle.getText().length() == 0) {
                Toast.makeText(DetailActivity.this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                return false;
            }

            // 내용 입력했는지 확인
            if( etDescription.getText() == null || etDescription.getText().length() == 0) {
                Toast.makeText(DetailActivity.this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                return false;
            }

            // MemoId 있으면 Modify, 없으면 Insert
            if( memo != null && memo.getMemoId() != null) {
                Log.d("MEMO", "Modify DetailActivity");
                param.put("code", "modifyMemo");
                param.put("memoId", memo.getMemoId());
                param.put("title", etTitle.getText().toString());
                param.put("description", etDescription.getText().toString());
                networkTask.execute(param);
                this.goMainActivity();
            } else {
                Log.d("MEMO", "Insert DetailActivity");
                param.put("code", "addMemo");
                param.put("title", etTitle.getText().toString());
                param.put("description", etDescription.getText().toString());
                networkTask.execute(param);
                this.goMainActivity();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Main Activity로 이동
     */
    public void goMainActivity() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
