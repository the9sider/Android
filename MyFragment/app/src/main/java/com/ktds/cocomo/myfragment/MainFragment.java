package com.ktds.cocomo.myfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 화면(Fragment)마다 타이틀을 다르게 줄 수 있다.
        getActivity().setTitle("프래그먼트 타이틀");

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        // 해당 프래그먼트가 보여줄 Layout을 정의한다.
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main, container, false);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getSupportActionBar().show();
            }
        });

        return layout;
    }
}
