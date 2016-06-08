package com.ktds.cocomo.addressbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.cocomo.addressbook.db.AddressBookDB;
import com.ktds.cocomo.addressbook.vo.AddressBookVO;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvPhoneNumber;
    private TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = (TextView) findViewById(R.id.tvName);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        Intent intent = getIntent();

        String key = intent.getStringExtra("key");

        AddressBookVO arrdessBook = AddressBookDB.getAddressBook(key);

        tvName.setText(arrdessBook.getName());
        tvPhoneNumber.setText(arrdessBook.getPhoneNumber());
        tvAddress.setText(arrdessBook.getAddress());

        tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "전화를 겁니다", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvPhoneNumber.getText()));
                startActivity(callIntent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(DetailActivity.this, "리스트로 이동합니다.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
