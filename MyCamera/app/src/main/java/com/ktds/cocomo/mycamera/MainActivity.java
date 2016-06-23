package com.ktds.cocomo.mycamera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnTakePicture;
    private Button btnSendPicture;
    private ImageView ivPicture;
    private String imagePath;
    private String encodedImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnSendPicture = (Button) findViewById(R.id.btnSendPicture);
        ivPicture = (ImageView) findViewById(R.id.ivPicture);

        // 사진찍기 버튼 클릭 시
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Camera Application이 있으면
                if (isExistCameraApplication()) {

                    // Camera Application을 실행한다.
                    Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // 찍은 사진을 보관할 파일 객체를 만들어서 보낸다.
                    File picture = savePictureFile();

                    if (picture != null) {
                        cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                        startActivityForResult(cameraApp, 10000);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "카메라 앱을 설치하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 사진 보내기 버튼 클릭 시
        btnSendPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이미지를 Base64를 통해 암호화시켜 문자열로 받는다.
                Base64Utility base64 = new Base64Utility();
                encodedImageString = base64.encodeJPEG(imagePath);

                Log.d("RESULT", encodedImageString);

                // 인터넷 접근 권한을 얻어온다.
                PermissionRequester.Builder requester = new PermissionRequester.Builder(MainActivity.this);
                int result =
                        requester
                                .create()
                                .request(Manifest.permission.INTERNET,
                                        30000,
                                        new PermissionRequester.OnClickDenyButtonListener() {
                                            @Override
                                            public void onClick(Activity activity) {
                                                Toast.makeText(MainActivity.this, "사진 보내기를 취소했습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                if (result == PermissionRequester.ALREADY_GRANTED
                        || result == PermissionRequester.REQUEST_PERMISSION) {

                    // 사진을 보낸다.
                    SendPictureTask task = new SendPictureTask();
                    task.execute(encodedImageString);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 30000
                && permissions[0].equals(Manifest.permission.INTERNET)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 사진을 보낸다.
            SendPictureTask task = new SendPictureTask();
            task.execute(encodedImageString);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 사진찍기 버튼을 누른 후 잘찍고 돌아왔다면
        if (requestCode == 10000 && resultCode == RESULT_OK) {

            // 사진을 ImageView에 보여준다.
            BitmapFactory.Options factory = new BitmapFactory.Options();
//            factory.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(imagePath);

            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, factory);
            ivPicture.setImageBitmap(bitmap);
        }

    }

    /**
     * Android에 Camera Application이 설치되어있는지 확인한다.
     *
     * @return 카메라 앱이 있으면 true, 없으면 false
     */
    private boolean isExistCameraApplication() {

        // Android의 모든 Application을 얻어온다.
        PackageManager packageManager = getPackageManager();

        // Camera Application
        Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // MediaStore.ACTION_IMAGE_CAPTURE을 처리할 수 있는 App 정보를 가져온다.
        List<ResolveInfo> cameraApps = packageManager.queryIntentActivities(
                cameraApp, PackageManager.MATCH_DEFAULT_ONLY);

        // 카메라 App이 적어도 한개 이상 있는지 리턴
        return cameraApps.size() > 0;
    }

    /**
     * 카메라에서 찍은 사진을 외부 저장소에 저장한다.
     *
     * @return
     */
    private File savePictureFile() {

        //외부저장소 쓰기 권한을 얻어온다.
        PermissionRequester.Builder requester = new PermissionRequester.Builder(this);

        int result = requester.create().request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                20000,
                new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {

                    }
                });

        //권한 수락시...
        if ( result == PermissionRequester.ALREADY_GRANTED || result == PermissionRequester.REQUEST_PERMISSION ) {

            // 사진 파일의 이름을 만든다.
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "IMG_" + timestamp;

            //사진 파일이 저장될 장소를 정한다.
            File pictureStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MYAPP/");

            // 사진 파일이 저장될 폴더가 존재하지 않는다면, 폴더를 새로 만들어준다.
            if (!pictureStorage.exists()) {
                pictureStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(fileName, ".jpg", pictureStorage);

                // 사진 파일의 절대 경로를 얻어온다.
                // 나중에 ImageView에 보여줄 때 필요하다.
                imagePath = file.getAbsolutePath();

                //찍힌 사진을 "갤러리" 앱에 추가한다.
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(imagePath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private class SendPictureTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpClient.Builder http = new HttpClient.Builder(
                    "POST"
                    , "http://10.225.152.191/SpringToAndroid/sendPicture");

            // 파라미터 추가
            http.addOrReplaceParameter("image", params[0]);

            HttpClient post = http.create();
            post.request();

            // 응답 코드
            int statusCode = post.getHttpStatusCode();
            Log.d("RESULT", statusCode + "");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "사진을 전송했습니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
