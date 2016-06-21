package com.ktds.cocomo.memowithweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cocomo.memowithweb.db.DBHelper;
import com.ktds.cocomo.memowithweb.vo.MemoVo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvMemoList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMemoList = (ListView) findViewById(R.id.lvMemoList);
        if(dbHelper == null) {
            dbHelper = new DBHelper(MainActivity.this, "MEMO2", null, 1);
        }

        setTitle("메모");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // "Fab Icon" 누르면 DetailActivity로 이동
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        List<MemoVo> memoList = dbHelper.getAllMemo();
        if (memoList != null && memoList.size() > 0) {
            lvMemoList.setAdapter(new MemoListAdapter(memoList, MainActivity.this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_write) {

            // "작성" 버튼을 누르면 DetailActivity로 이동
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Memo List Adapter
     */
    private class MemoListAdapter extends BaseAdapter {

        private List<MemoVo> memoList;
        private Context context;

        public MemoListAdapter(List<MemoVo> memoList, Context context) {
            this.memoList = memoList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.memoList.size();
        }

        @Override
        public Object getItem(int position) {
            return this.memoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;

            if(convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_list, parent, false);

                holder = new Holder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }

            final MemoVo memo = (MemoVo) getItem(position);
            holder.tvTitle.setText(memo.getTitle());
            holder.tvDate.setText(memo.getCreatedDate());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("memo", memo);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    private class Holder {
        public TextView tvTitle;
        public TextView tvDate;
    }
}
