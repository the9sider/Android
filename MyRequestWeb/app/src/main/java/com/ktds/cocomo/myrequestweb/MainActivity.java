package com.ktds.cocomo.myrequestweb;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends ActionBarActivity {

    private Button btnSearch;
    private EditText etURL;
    private TextView tvResult;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        etURL = (EditText) findViewById(R.id.etURL);
        tvResult = (TextView) findViewById(R.id.tvResult);
        handler = new Handler();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * HTTP로 요청을 보낸다. Thread 작업이 필요하다.
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        /**
                         * 작업 시작
                         */
                        String url = etURL.getText().toString();
                        try {

                            /**
                             * 요청을 보낼 URL 정보
                             */
                            URL httpURL = new URL(url);

                            //  요청을 보내기 위한 준비를 한다.
                            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
                            conn.setDoInput(true);
                            conn.setDoOutput(true);

                            /**
                             * 최대 요청 지연 시간
                             * 요청이 5초 이상 걸릴경우 요청을 끊는다.
                             */
                            conn.setConnectTimeout(5000);

                            /**
                             * GET 요청을 한다.
                             * POST 요청을 원할 경우 "POST"라고 작성한다.
                             */
                            conn.setRequestMethod("GET");

                            /**
                             * 요청을 보내고
                             * 동시에 응답을 받는다.
                             */
                            int responseCode = conn.getResponseCode();

                            /**
                             * 요청과 응답이 제대로 이루어졌는지 검사한다.
                             * HttpURLConnection.HTTP_OK : 응답이 200 OK 라는 의미
                             */
                            if (responseCode == HttpURLConnection.HTTP_OK) {

                                /**
                                 * 응답 본문 전체를 담는다.
                                 */
                                final StringBuffer responseBody = new StringBuffer();

                                /**
                                 * 응답 본문을 한 줄씩 읽어온다.
                                 */
                                String line = null;

                                /**
                                 * 응답 본문을 담고있는 InputStream을 받아온다.
                                 * BufferedReader는 InputStream을 한 줄씩 얻어올 수 있는 객체이다.
                                 */
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                                /**
                                 * 응답 본문이 종료될 때까지 반복한다.
                                 */
                                while ( (line = reader.readLine()) != null ) {

                                    /**
                                     * 응답 본문 한 줄씩 결과객체에 담는다.
                                     * 줄바꿈을 위해서 매 라인 끝마다 "\n"을 더해준다.
                                     */
                                    responseBody.append(line + "\n");
                                }

                                /**
                                 * 연결을 순차적으로 끊는다.
                                 */
                                reader.close();
                                conn.disconnect();

                                /**
                                 * 독립된 Thread에서 Android Application의 Main Thread로
                                 * 접근할 수 있는 Handler를 만들어서 UI View를 컨트롤한다.
                                 */
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        /**
                                         * 결과를 사용자에게 보여준다.
                                         */
                                        tvResult.setText(responseBody.toString());
                                    }
                                });

                            }

                        } catch (MalformedURLException e) {
                            return;
                            // throw new RuntimeException(e.getMessage(), e);
                        } catch (IOException e) {
                            return;
                        }
                    }
                }).start();
            }
        });
    }
}
