package com.ktds.cocomo.calculator2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnDone;
    private String operator;
    private int firstNum;
    private int secondNum;
    private int result;
    private String equation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView) findViewById(R.id.tvResult);
        btnDone = (Button) findViewById(R.id.btnDone);

        Intent intent = getIntent();
        operator = intent.getStringExtra("operator");
        firstNum = Integer.parseInt(intent.getStringExtra("firstNum"));
        secondNum = Integer.parseInt(intent.getStringExtra("secondNum"));
        result = getResult(firstNum, secondNum, operator);
        equation = firstNum + " " + operator + " " + secondNum + " = " + result;

        tvResult.setText(equation);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("equation", equation);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    private int getResult(int firstNum, int secondNum, String operator) {

        if(operator.equals("+")) {
            return firstNum + secondNum;
        } else if(operator.equals("-")) {
            return firstNum - secondNum;
        } else if(operator.equals("x")) {
            return firstNum * secondNum;
        } else if(operator.equals("/")) {
            return (int) firstNum / secondNum;
        } else {
            return 0;
        }
    }
}
