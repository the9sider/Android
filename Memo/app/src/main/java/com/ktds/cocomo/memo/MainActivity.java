package com.ktds.cocomo.memo;

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

import com.ktds.cocomo.memo.db.DBHelper;
import com.ktds.cocomo.memo.vo.MemoVO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvMemoList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMemoList = (ListView) findViewById(R.id.lvMemoList);

        if( dbHelper == null ) {
            dbHelper = new DBHelper(MainActivity.this, "MEMO", null, 1);
        }

        setTitle("메모");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });

        List<MemoVO> memoList = dbHelper.getAllMemo();
        if(memoList != null && memoList.size() > 0) {
            lvMemoList.setAdapter(new MemoListAdapter(memoList, MainActivity.this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_write) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MemoListAdapter extends BaseAdapter {

        private List<MemoVO> memoList;
        private Context context;

        public MemoListAdapter(List<MemoVO> memoList, Context context) {
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

            final MemoVO memo = (MemoVO) getItem(position);
            holder.tvTitle.setText(memo.getTitle());

//            String time = memo.getTime() + "";
//            long currTime = System.currentTimeMillis();

//            if( (currTime - time) > 1440 ) {
                holder.tvDate.setText(memo.getDate() + "");
//            } else {
//                holder.tvDate.setText(memo.getTime() + "");
//            }

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
