package com.ktds.cocomo.mydatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ktds.cocomo.mydatabase.db.DBHelper;
import com.ktds.cocomo.mydatabase.vo.Person;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAge;
    private TextView tvPhone;
    private TextView tvAddress;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = (TextView) findViewById(R.id.tvName);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        int _id = getIntent().getIntExtra("_id", 0);
        if(dbHelper == null) {
            dbHelper = new DBHelper(DetailActivity.this, "TEST", null , DBHelper.DB_VERSION);
        }

        Person person = dbHelper.getPersonById(_id);

        tvName.setText(person.getName());
        tvAge.setText(person.getAge() + "");
        tvPhone.setText(person.getPhone());
        tvAddress.setText(person.getAddress());
    }
}
