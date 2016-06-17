package com.ktds.cocomo.mymultifragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements FragmentReplaceable {

    private Fragment firstFragment;
    private Fragment secondFragment;
    private Fragment thirdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        setDefaultFragment();
    }

    /**
     * MainActivity가 처음 실행될 때
     * 최초로 보여질 Fragment를 세팅한다.
     */
    public void setDefaultFragment() {

        /**
         * 화면에 보여지는 Fragment를 관리한다.
         * FragmentManager : Fragment를 바꾸거나 추가하는 객체
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /**
         * R.id.container(activity_main.xml)에 띄우겠다.
         * 첫번째로 보여지는 Fragment는 firstFragment로 설정한다.
         * 프래그먼트를 생성하면서 보낸다.
         */
        firstFragment = FirstFragment.newInstance(

                // Random 숫자 6개 만들어서 파라미터로 보낸다.
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1
        );
        transaction.add(R.id.container, firstFragment);

        /**
         * Fragment의 변경사항을 반영시킨다.
         */
        transaction.commit();
    }

    /**
     * Fragment 변경
     * @see FragmentReplaceable
     * @param fragmentId : 보여질 Fragment
     */
    @Override
    public void replaceFragment(int fragmentId) {

        /**
         * 화면에 보여지는 Fragment를 관리한다.
         * FragmentManager : Fragment를 바꾸거나 추가하는 객체
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /**
         * R.id.container(activity_main.xml)에 띄우겠다.
         * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다.
         */
        if ( fragmentId == 1 ) {
            transaction.replace(R.id.container, new FirstFragment());
            // transaction.replace(R.id.container, FirstFragment.newInstance("", ""));
        } else if ( fragmentId == 2 ) {
            transaction.replace(R.id.container, new SecondFragment());
        } else if ( fragmentId == 3 ) {
            transaction.replace(R.id.container, new ThirdFragment());
        }

        /**
         * Back 버튼 클릭시 이전 프래그먼트로 이동시킨다.
         */
        transaction.addToBackStack(null);

        /**
         * Fragment의 변경사항을 반영시킨다.
         */
        transaction.commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {

        /**
         * 화면에 보여지는 Fragment를 관리한다.
         * FragmentManager : Fragment를 바꾸거나 추가하는 객체
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /**
         * R.id.container(activity_main.xml)에 띄우겠다.
         * 파라미터로 오는 fragment 따라 다음에 보여질 Fragment를 설정한다.
         */
        if( fragment instanceof FirstFragment ) {
            firstFragment = fragment;
            transaction.replace(R.id.container, firstFragment);
        } else if( fragment instanceof SecondFragment ) {
            secondFragment = fragment;
            transaction.replace(R.id.container, secondFragment);
        }  else if( fragment instanceof ThirdFragment ) {
            thirdFragment = fragment;
            transaction.replace(R.id.container, thirdFragment);
        }

        /**
         * Back 버튼 클릭시 이전 프래그먼트로 이동시킨다.
         */
        transaction.addToBackStack(null);

        /**
         * Fragment의 변경사항을 반영시킨다.
         */
        transaction.commit();
    }
}
