package com.ktds.cocomo.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Java의 메인문 역할을 한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // R : Resources : res
        // res > layout > activity_main 을 띄운다.
        setContentView(R.layout.activity_main);

        // Alt + Enter
        // Casting
        final Button button1 = (Button) findViewById(R.id.btn1);

        // Template Call Back (익명 클래스)
        // 인터페이스를 만들어 놓고 구현체를 만들지 않고 이런식으로 바로 사용한다.
        // button1이 클릭이 되는지 지켜보다가, 클릭이 되면 안에 내용을 수행한다.
        button1.setOnClickListener(new View.OnClickListener() {

            // v에는 button1이 들어있다.
            @Override
            public void onClick(View v) {
                // 여기서 사용되는 button1이 위에서 사용하는 button1이 맞는지 모르기때문에
                // button1에 final 키워드를 이용해 상수화 시켜야 이용할 수 있다.
                // Toast.makeText(v.getContext(), button1.getText(), Toast.LENGTH_SHORT).show();

                // v를 button으로 캐스팅 한 뒤에 get Text하면 위의 코드와 같은 효과를 볼 수 있다.
                Toast.makeText(v.getContext(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 화면에 보이는 것들은 모두 view 라고 부르며, view는 이벤트를 발생시킬 수 있다.
    // call 메소드를 부른 view를 받아온다.
    public void call(View view) {

        // Android는 모든 객체의 id를 숫자로 관리한다.
        int id = view.getId();

        if( id == R.id.button) {
            Toast.makeText(view.getContext(), "버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-3333-4444"));
            startActivity(intent);
        } else if( id == R.id.textView ) {
            Toast.makeText(view.getContext(), "텍스트를 눌렀습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
