package com.ktds.cocomo.mydrawablelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment1;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment2;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment3;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment4;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment5;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment6;

public class GalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ViewPager pager;
    private PagerSlidingTabStrip tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // View Pager를 선언합니다.
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter( new PagerAdapter( getChildFragmentManager()));
        pager.setOffscreenPageLimit(6);

        // 처음으로 0번째 Fragment를 보여줍니다.
        pager.setCurrentItem(0);

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs) ;
        tabs.setViewPager(pager);

        // Title을 설정합니다.
        getActivity().setTitle("Gallery Fragment");

        // Inflate the layout for this fragment
        return view;
    }

    // 페이지마다 보여줄 타이틀을 정해준다.
    private String[] pageTitle = {"Page 1", "Page 2", "Page 3",
                                    "Page 4", "Page 5", "Page 6"};

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }

        /**
         * View Pager의 Fragment 들은 각각 Index를 가진다.
         * Android OS로 부터 요청된 Pager의 Index를 보내주면,
         * 해당되는 Fragment를 리턴시킨다.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {

            if( position == 0 ) {
                return new GalleryFragment1();
            } else if ( position == 1 ) {
                return new GalleryFragment2();
            }  else if (position == 2) {
                return new GalleryFragment3();
            }  else if (position == 3) {
                return new GalleryFragment4();
            }  else if (position == 4) {
                return new GalleryFragment5();
            }  else {
                return new GalleryFragment6();
            }
        }

        /**
         * View Pager에 몇개의 Fragment가 들어가는지 설정
         * @return
         */
        @Override
        public int getCount() {
            return 6;
        }
    }
}
