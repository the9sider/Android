package com.ktds.cocomo.landscapelayout;

import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.etText);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        String text = etText.getText().toString();

        setContentView(R.layout.activity_main);
        etText = (EditText) findViewById(R.id.etText);
        etText.setText(text);

    }
}
