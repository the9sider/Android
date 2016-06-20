package com.example.kgm.lgtwins.board;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kgm.lgtwins.FragmentReplaceable;
import com.example.kgm.lgtwins.R;

import java.util.List;

public class BoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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

    private ListView lvArticleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        lvArticleList = (ListView) view.findViewById(R.id.lvArticleList);
        BoardDB.prepareData();

        lvArticleList.setAdapter(new ArticleListViewAdapter(BoardDB.getArticleList(), getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    /**
     *
     */
    private class ArticleListViewAdapter extends BaseAdapter {

        /**
         * ListView에 세팅할 Item 정보들
         */
        private List articleList;

        /**
         * ListView에 Item을 세팅할 요청자의 정보가 들어감
         */
        private Context context;

        /**
         * 생성자
         *
         * @param articleList
         * @param context
         */
        public ArticleListViewAdapter(List articleList, Context context) {
            this.articleList = articleList;
            this.context = context;
        }

        /**
         * ListView에 세팅할 아이템의 갯수
         * @return
         */
        @Override
        public int getCount() {
            return articleList.size();
        }

        /**
         * position 번째 Item 정보를 가져옴
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return articleList.get(position);
        }

        /**
         * 아이템의 index를 가져옴
         * Item index == position
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * ListView에 Item들을 세팅함
         * position 번 째 있는 아이템을 가져와서 converView에 넣은다음 parent에서 보여주면된다.
         * @param position : 현재 보여질 아이템의 인덱스, 0 ~ getCount() 까지 증가
         * @param convertView : ListView의 Item Cell(한 칸) 객체를 가져옴
         * @param parent : ListView
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ItemHolder holder = null;

            /**
             * 가장 간단한 방법
             * 사용자가 처음으로 Flicking을 할 때, 아래쪽에 만들어지는 Cell(한 칸)은 Null이다.
             */
            if( convertView == null ) {

                // Item Cell에 Layout을 적용시킬 Inflater 객체를 생성한다.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Item Cell에 Layout을 적용시킨다.
                // 실제 객체는 이곳에 있다.
                convertView = inflater.inflate(R.layout.item_list, parent, false);
                holder = new ItemHolder();
            } else {
                holder = (ItemHolder) convertView.getTag();
            }

            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            holder.tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);

            final ArticleVO article = (ArticleVO) getItem(position);
            holder.tvSubject.setText(article.getSubject());
            holder.tvAuthor.setText(article.getAuthor());
            holder.tvHitCount.setText(article.getHitCount() + "");
            holder.description = article.getDescription();

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentReplaceable)getActivity()).replaceFragment(article);
                }
            });

            convertView.setTag(holder);

            return convertView;
        }

        private class ItemHolder {
            public TextView tvSubject;
            public TextView tvAuthor;
            public TextView tvHitCount;
            public String description;
        }
    }
}