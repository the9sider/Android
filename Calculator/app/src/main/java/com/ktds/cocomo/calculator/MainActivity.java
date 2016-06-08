package com.ktds.cocomo.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;


    private Button buttonAdd;
    private Button buttonSub;
    private Button buttonEqual;
    private Button buttonReset;
    private TextView textView;

    private int calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 =  (Button) findViewById(R.id.btn1);
        button2 =  (Button) findViewById(R.id.btn2);
        button3 =  (Button) findViewById(R.id.btn3);
        button4 =  (Button) findViewById(R.id.btn4);
        button5 =  (Button) findViewById(R.id.btn5);
        button6 =  (Button) findViewById(R.id.btn6);
        button7 =  (Button) findViewById(R.id.btn7);
        button8 =  (Button) findViewById(R.id.btn8);
        button9 =  (Button) findViewById(R.id.btn9);
        button0 =  (Button) findViewById(R.id.btn10);
        buttonAdd = (Button) findViewById(R.id.btn11);
        buttonSub = (Button) findViewById(R.id.btn11);
        buttonEqual = (Button) findViewById(R.id.btn11);
        buttonReset = (Button) findViewById(R.id.btn11);
        textView = (TextView) findViewById(R.id.textView);
        calc = 0;

        final Map<String, Integer> numbers = new HashMap();
        numbers.put("number", 0);

        // Number
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = "";
                text = (String) textView.getText();
                text += ((Button) v).getText();
                int newNumber = Integer.parseInt(text);
                textView.setText(newNumber + "");
            }
        };

        // Operator
        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.equals(buttonAdd)) {

                    int num1 = numbers.get("number");
                    String text = (String) textView.getText();
                    int num2 = Integer.parseInt(text);
                    int result = getResult(num1, num2, calc);
                    numbers.put("number", result);
                    calc = 1;
                    textView.setText("0");

                } else if(v.equals(buttonSub)) {

                    int num1 = numbers.get("number");
                    String text = (String) textView.getText();
                    int num2 = Integer.parseInt(text);
                    int result = getResult(num1, num2, calc);
                    numbers.put("number", result);
                    calc = 2;
                    textView.setText("0");

                } else if (v.equals(buttonReset)) {

                    numbers.put("number", 0);
                    calc = 0;

                } else if(v.equals(buttonEqual)) {

                    int num1 = numbers.get("number");
                    String text = (String) textView.getText();
                    int num2 = Integer.parseInt(text);
                    int result = getResult(num1, num2, calc);
                    numbers.put("number", 0);
                    calc = 0;
                    textView.setText(result + "");
                } else {
                    calc = 0;
                }


            }
        };

        button1.setOnClickListener(numberListener);
        button2.setOnClickListener(numberListener);
        button3.setOnClickListener(numberListener);
        button4.setOnClickListener(numberListener);
        button5.setOnClickListener(numberListener);
        button6.setOnClickListener(numberListener);
        button7.setOnClickListener(numberListener);
        button8.setOnClickListener(numberListener);
        button9.setOnClickListener(numberListener);
        button0.setOnClickListener(numberListener);

        buttonAdd.setOnClickListener(operatorListener);
        buttonSub.setOnClickListener(operatorListener);
        buttonEqual.setOnClickListener(operatorListener);
        buttonReset.setOnClickListener(operatorListener);
    }

    public int getResult(int num1, int num2, int calc) {

        int result = 0;

        if(calc == 0) {
            result = num2;
        } else if(calc == 1) {
            result = num1 + num2;
        } else if(calc == 2){
            result = num1 - num2;
        }
        return result;
    }
}
