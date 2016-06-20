package com.ktds.cocomo.mydatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.cocomo.mydatabase.db.DBHelper;
import com.ktds.cocomo.mydatabase.vo.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateDatabase;
    private Button btnInsertDatabase;
    private Button btnSelectAllData;
    private ListView lvPeople;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateDatabase = (Button) findViewById(R.id.btnCreateButton);
        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Data List가 안보이도록 한다.
                lvPeople.setVisibility(View.INVISIBLE);

                final EditText etDBName = new EditText(MainActivity.this);
                etDBName.setHint("DB명을 입력하세요.");

                // Dialog로 Database의 이름을 입력받는다.
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Database 이름을 입력하세요.")
                        .setMessage("Database 이름을 입력하세요.")
                        .setView(etDBName)
                        .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if( etDBName.getText().toString().length() > 0 ) {
                                    dbHelper = new DBHelper(
                                            MainActivity.this, etDBName.getText().toString(),
                                            null, DBHelper.DB_VERSION);
                                    dbHelper.testDB();
                                }

                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "취소했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });

        btnInsertDatabase = (Button) findViewById(R.id.btnInsertButton);
        btnInsertDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Data List가 안보이도록 한다.
                lvPeople.setVisibility(View.INVISIBLE);

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("이름을 입력하세요.");

                final EditText etAge = new EditText(MainActivity.this);
                etAge.setHint("나이를 입력하세요.");

                final EditText etPhone = new EditText(MainActivity.this);
                etPhone.setHint("전화번호를 입력하세요.");

                final EditText etAddress = new EditText(MainActivity.this);
                etAddress.setHint("주소를 입력하세요.");

                layout.addView(etName);
                layout.addView(etAge);
                layout.addView(etPhone);
                layout.addView(etAddress);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("정보를 입력하세요")
                        .setView(layout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etName.getText().toString();
                                int age = Integer.parseInt( etAge.getText().toString() );
                                String phone = etPhone.getText().toString();
                                String address = etAddress.getText().toString();

                                if( dbHelper == null ) {
                                    dbHelper = new DBHelper(MainActivity.this, "TEST", null , DBHelper.DB_VERSION);
                                }

                                Person person = new Person();
                                person.setName(name);
                                person.setAge(age);
                                person.setPhone(phone);
                                person.setAddress(address);

                                dbHelper.addPerson(person);
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "취소했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });

        lvPeople = (ListView) findViewById(R.id.lvPeople);
        btnSelectAllData = (Button) findViewById(R.id.btnSelectAllData);
        btnSelectAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ListView를 보여준다.
                lvPeople.setVisibility(View.VISIBLE);

                // DB Helper가 Null이면 초기화 시켜준다.
                if( dbHelper == null ) {
                    dbHelper = new DBHelper(MainActivity.this,"TEST", null , DBHelper.DB_VERSION);
                }

                // 1. Person 데이터를 모두 가져온다.
                List<Person> people
                        = dbHelper.getAllPersonData();

                // 2. ListView에 Person 데이터를 모두 보여준다.
                lvPeople.setAdapter(new PersonListAdapter(people, MainActivity.this));
            }
        });
    }

    /**
     * Person List Adapter
     */
    private class PersonListAdapter extends BaseAdapter {

        private List<Person> people;
        private Context context;

        /**
         * 생성자
         * @param people : Person List
         * @param context
         */
        public PersonListAdapter(List<Person> people, Context context) {
            this.people = people;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.people.size();
        }

        @Override
        public Object getItem(int position) {
            return this.people.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;

            if( convertView == null ) {

                // convertView가 없으면 초기화합니다.
                convertView = new LinearLayout(context);
                ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);

                TextView tvId = new TextView(context);
                tvId.setPadding(10, 0, 20, 0);
                tvId.setTextColor(Color.rgb(0, 0, 0));

                TextView tvName = new TextView(context);
                tvName.setPadding(20, 0, 20, 0);
                tvName.setTextColor(Color.rgb(0, 0, 0));

                TextView tvAge = new TextView(context);
                tvAge.setPadding(20, 0, 20, 0);
                tvAge.setTextColor(Color.rgb(0, 0, 0));

                TextView tvPhone = new TextView(context);
                tvPhone.setPadding(20, 0, 20, 0);
                tvPhone.setTextColor(Color.rgb(0, 0, 0));

                TextView tvAddress = new TextView(context);
                tvAddress.setPadding(20, 0, 20, 0);
                tvAddress.setTextColor(Color.rgb(0, 0, 0));

                ( (LinearLayout) convertView).addView(tvId);
                ( (LinearLayout) convertView).addView(tvName);
                ( (LinearLayout) convertView).addView(tvAge);
                ( (LinearLayout) convertView).addView(tvPhone);
                ( (LinearLayout) convertView).addView(tvAddress);

                holder = new Holder();
                holder.tvId = tvId;
                holder.tvName = tvName;
                holder.tvAge = tvAge;
                holder.tvPhone = tvPhone;
                holder.tvAddress = tvAddress;

                convertView.setTag(holder);
            } else {

                // convertView가 있으면 홀더를 꺼냅니다.
                holder = (Holder) convertView.getTag();
            }

            // 한명의 데이터를 받아와서 입력합니다.
            final Person person = (Person) getItem(position);
            holder.tvId.setText(person.get_id() + "");
            holder.tvName.setText(person.getName());
            holder.tvAge.setText(person.getAge() + "");
            holder.tvPhone.setText(person.getPhone());
            holder.tvAddress.setText(person.getAddress());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("_id", person.get_id());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    /**
     * 홀더
     */
    private class Holder {
        public TextView tvId;
        public TextView tvName;
        public TextView tvAge;
        public TextView tvPhone;
        public TextView tvAddress;
    }
}
