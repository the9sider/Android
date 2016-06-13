package com.ktds.cocomo.callapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = new PermissionRequester.Builder(MainActivity.this)
                        .setTitle("권한 요청")
                        .setMessage("권한을 요청합니다.")
                        .setPositiveButtonName("네")
                        .setNegativeButtonName("아니요.")
                        .create()
                        .request(Manifest.permission.CALL_PHONE, 1000, new PermissionRequester.OnClickDenyButtonListener() {
                            @Override
                            public void onClick(Activity activity) {
                                Log.d("xxx", "취소함.");
                            }
                        });

                if (result == PermissionRequester.ALREADY_GRANTED) {
                    Log.d("RESULT", "권한이 이미 존재함.");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
                        startActivity(intent);
                    }
                } else if (result == PermissionRequester.NOT_SUPPORT_VERSION) {
                    Log.d("RESULT", "마쉬멜로우 이상 버젼 아님.");
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
                    startActivity(intent);
                } else if (result == PermissionRequester.REQUEST_PERMISSION) {
                    Log.d("RESULT", "요청함. 응답을 기다림.");
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 권한 요청에 대한 응답을 이곳에서 가져온다.
     *
     * @param requestCode  요청코드
     * @param permissions  사용자가 요청한 권한들
     * @param grantResults 권한에 대한 응답들(인덱스별로 매칭)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1000) {

            // 요청한 권한을 사용자가 "허용" 했다면...
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));

                // Add Check Permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(MainActivity.this, "권한요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
