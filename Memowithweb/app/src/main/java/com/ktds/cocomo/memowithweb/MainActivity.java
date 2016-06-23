package com.ktds.cocomo.memowithweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvMemoList;
    private DBHelper dbHelper;
    private List<MemoVo> memoList;

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
                startActivityForResult(intent, 10000);
            }
        });

        memoList = dbHelper.getAllMemo();
        if (memoList != null && memoList.size() > 0) {
            lvMemoList.setAdapter(new MemoListAdapter(memoList, MainActivity.this));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 10000 && resultCode == RESULT_OK ) {
            Log.d("RESULT", "...");

            List<MemoVo> tempMemo = dbHelper.getAllMemo();
            memoList.clear();
            memoList.addAll(tempMemo);

            Log.d("RESULT", memoList.size() + "");
            ( (MemoListAdapter) lvMemoList.getAdapter() ).notifyDataSetChanged();
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
            startActivityForResult(intent, 10000);
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

            String memoDate = memo.getCreatedDate();
            String[] split = memoDate.split("-");
            String realDate = split[0];
            realDate = realDate.trim();

            long now = System.currentTimeMillis();
            // 1000 * 60 * 60
            now = now + 36000000;
            Date date = new Date(now);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String nowDate = dateFormat.format(date);
            nowDate = nowDate.trim();

            if( nowDate.equals(realDate)  ) {
                holder.tvDate.setText(split[1]);
            }
            holder.tvDate.setText(split[0]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("memo", memo);
                    startActivityForResult(intent, 10000);
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
