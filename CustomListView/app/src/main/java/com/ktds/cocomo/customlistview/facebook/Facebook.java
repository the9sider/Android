package com.ktds.cocomo.customlistview.facebook;

import android.content.Context;
import android.util.Log;

import com.ktds.cocomo.customlistview.MainActivity;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-032 on 2016-06-14.
 */
public class Facebook {

    /**
     * 인증과 관련된 상수들
     */
    private static final String APP_ID = "453904898150515";
    private static final String APP_SECRET = "394c32c954e3074738e6e07a750acf6a";
    private static final String ACCESS_TOKEN = "EAAGc0vg3UHMBAG8zEaMwT6SIDg0rDBaEyZC4nnEjA06EYqa2I99EYeKc6r8bsPUS0tq5t4Y3oeE4ZBUalH3gl8eY9nUb22qLsCJOc43iZAS2wc1QkbOFs1MOT8tGldaHKi8EhvNRj4F6x5Xlxw0doZBX13ikDOlYC3KPZCZC6kXgZDZD";

    private Context context;

    public Facebook(Context context) {
        this.context = context;
    }

    /**
     * Facebook 인증 객체
     */
    private FacebookClient myFacebook;

    /**
     * 로그인 됐는지 확인하는 변수
     */
    private boolean isLogin;

    /**
     * Facebook으로 로그인 한다.
     *
     * @return : 로그인 성공시 true
     */
    public void auth() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                // Facebook Login
                myFacebook = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);

                /**
                 * 로그인 성공했는지 체크한다.
                 * 로그인된 계정의 정보를 가져온다.
                 */
                User me = myFacebook.fetchObject("me", User.class);
                Log.d("FACEBOOK", me.getName() + " 계정으로 로그인 함");

                // 로그인 되었으면 isLogin = true
                isLogin = (me != null);
                if (isLogin) {
                    ((MainActivity) context).setTimeline();
                }
            }
        }).start();
    }

    /**
     * 로그인 됐는지 확인
     *
     * @return
     */
    public boolean isLogin() {
        return isLogin;
    }

    /**
     *
     * @param timelineSerializable : 쓰레드안의 run에서 사용하기 위해 final 붙인다.
     */
    public void getTimeLine( final TimelineSerializable timelineSerializable ) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                /**
                 * 나의 타임라인의 모든 포스트를 Post라는 클래스 형태로 가져온다.
                 */
                Connection<Post> feeds = myFacebook.fetchConnection("me/feed", Post.class, Parameter.with("fields", "from,likes,message,story"));

                List<Post> postList = new ArrayList<Post>();

                /**
                 * 타임라인 정보들
                 */
                for (List<Post> posts : feeds) {
                    postList.addAll(posts);
                }
                timelineSerializable.serialize(postList);
            }
        }).start();
    }

    public interface TimelineSerializable {
        public void serialize(List<Post> posts);
    }
}
