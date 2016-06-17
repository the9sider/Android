package com.ktds.cocomo.mydrawablelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment1;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment2;
import com.ktds.cocomo.mydrawablelayout.gallery.GalleryFragment3;

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

    private Button btnFirstGallery;
    private Button btnSecondGallery;
    private Button btnThirdGallery;

    private ViewPager pager;

    private Fragment galleryFragment1;
    private Fragment galleryFragment2;
    private Fragment galleryFragment3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryFragment1 = new GalleryFragment1();
        galleryFragment2 = new GalleryFragment2();
        galleryFragment3 = new GalleryFragment3();

        btnFirstGallery = (Button) view.findViewById(R.id.btnFirstGallery);
        btnFirstGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        btnSecondGallery = (Button) view.findViewById(R.id.btnSecondGallery);
        btnSecondGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        btnThirdGallery = (Button) view.findViewById(R.id.btnThirdGallery);
        btnThirdGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });

        // View Pager를 선언합니다.
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter( new PagerAdapter(getActivity().getSupportFragmentManager()));

        // 처음으로 0번째 Fragment를 보여줍니다.
        pager.setCurrentItem(0);

        // Title을 설정합니다.
        getActivity().setTitle("Gallery Fragment");

        // Inflate the layout for this fragment
        return view;
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
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
                return galleryFragment1;
            } else if ( position == 1 ) {
                return galleryFragment2;
            }  else {
                return galleryFragment3;
            }
        }

        /**
         * View Pager에 몇개의 Fragment가 들어가는지 설정
         * @return
         */
        @Override
        public int getCount() {
            return 3;
        }
    }
}
