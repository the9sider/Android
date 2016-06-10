package com.ktds.cocomo.calculator2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    private EditText etFirst;
    private EditText etSecond;
    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;
    private TextView tvResult;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etFirst = (EditText) findViewById(R.id.etFirst);
        etSecond = (EditText) findViewById(R.id.etSecond);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        tvResult = (TextView) findViewById(R.id.tvResult);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNum = etFirst.getText().toString();
                String secondNum = etSecond.getText().toString();
                String operator = (String) ((Button) v).getText();

                if( firstNum.length() == 0 ) {
                    Toast.makeText(InputActivity.this, "첫번째 값을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etFirst.requestFocus();
                    return;
                } else if ( secondNum.length() == 0 ) {
                    Toast.makeText(InputActivity.this, "두번째 값을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etSecond.requestFocus();
                    return;
                }

                if(operator.equals("/") && secondNum.equals("0")) {
                    Toast.makeText(InputActivity.this, "0 으로 나눌 수 없습니다!", Toast.LENGTH_SHORT).show();
                    etSecond.requestFocus();
                    return;
                }

                Intent intent = new Intent(v.getContext(), ResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("firstNum", firstNum);
                intent.putExtra("secondNum", secondNum);
                intent.putExtra("operator", operator);

                startActivityForResult(intent, 1000);
            }
        };

        btnAdd.setOnClickListener(buttonClickListener);
        btnSub.setOnClickListener(buttonClickListener);
        btnMul.setOnClickListener(buttonClickListener);
        btnDiv.setOnClickListener(buttonClickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            tvResult.setText(data.getStringExtra("equation"));
        }
    }

    @Override
    public void onBackPressed() {
        if(pressedTime == 0) {
            Toast.makeText(InputActivity.this, "한번 더 누르면 종료됩니다!", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        } else {

            long seconds = System.currentTimeMillis() - pressedTime;
            if ( seconds < 2000 ) {
                Toast.makeText(InputActivity.this, "종료합니다!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(InputActivity.this, "한번 더 누르면 종료됩니다!", Toast.LENGTH_SHORT).show();
                pressedTime = System.currentTimeMillis();
            }
        }
    }
}
