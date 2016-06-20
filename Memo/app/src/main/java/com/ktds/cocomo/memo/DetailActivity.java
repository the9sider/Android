package com.ktds.cocomo.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ktds.cocomo.memo.db.DBHelper;
import com.ktds.cocomo.memo.vo.MemoVO;

public class DetailActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);

        if(dbHelper == null) {
            dbHelper = new DBHelper(DetailActivity.this, "MEMO", null, 1);
        }

        final MemoVO memo = (MemoVO) getIntent().getSerializableExtra("memo");
        if(memo != null && ( memo.getTitle() != null || memo.getDescription() != null )) {
            etTitle.setText(memo.getTitle());
            etDescription.setText(memo.getDescription());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteMemoById(memo.get_id());
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_complete) {

            MemoVO memo = new MemoVO();

            if(etTitle.getText() == null) {
                Toast.makeText(DetailActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                memo.setTitle(etTitle.getText().toString());
            }

            if(etDescription.getText() == null) {
                Toast.makeText(DetailActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                memo.setDescription(etDescription.getText().toString());
            }

            dbHelper.addMemo(memo);

            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
