package com.example.kgm.lgtwins.board;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kgm.lgtwins.R;

public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArticleVO article;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param article Parameter 1.
     * @return A new instance of fragment BoardFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(ArticleVO article) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("article", article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            article = (ArticleVO) getArguments().getSerializable("article");
        }
    }

    private TextView tvSubject;
    private TextView tvAuthor;
    private TextView tvHitCount;
    private TextView tvDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        tvSubject = (TextView) view.findViewById(R.id.tvSubject);
        tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
        tvHitCount = (TextView) view.findViewById(R.id.tvHitCount);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);

        tvSubject.setText(article.getSubject());
        tvAuthor.setText(article.getAuthor());
        tvHitCount.setText(article.getHitCount() + "");
        tvDescription.setText(article.getDescription());

        return view;
    }
}
