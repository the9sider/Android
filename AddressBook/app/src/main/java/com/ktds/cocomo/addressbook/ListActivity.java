package com.ktds.cocomo.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ktds.cocomo.addressbook.db.AddressBookDB;
import com.ktds.cocomo.addressbook.vo.AddressBookVO;

public class ListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        prepareAddressBookDB();

        LinearLayout ll = (LinearLayout) findViewById(R.id.itemList);

        for(int i = 1; i < AddressBookDB.getIndexes().size(); i++ ) {
            Button button = new AppCompatButton(this);
            button.setText(AddressBookDB.getIndexes().get(i));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = (String) ((Button) v).getText();

                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("key", buttonText);
                    startActivity(intent);
                }
            });

            ll.addView(button);
        }

    }

    private void prepareAddressBookDB() {
        for (int i = 1; i < 10; i++) {
            AddressBookDB.addAddressBook("김광민 " + i, new AddressBookVO("김광민 " + i, "010-6291-1316", "양화대교 근처 " + i + "번지"));
        }
    }

    private long pressedTime;

    @Override
    public void onBackPressed() {
        if(pressedTime == 0) {
            Toast.makeText(ListActivity.this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        } else {

            long seconds = pressedTime - System.currentTimeMillis();

            if(seconds > 2000) {
                Toast.makeText(ListActivity.this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
                pressedTime = System.currentTimeMillis();
            } else {
                finish();
            }

        }

    }
}
