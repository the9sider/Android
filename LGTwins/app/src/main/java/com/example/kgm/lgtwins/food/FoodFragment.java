package com.example.kgm.lgtwins.food;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kgm.lgtwins.R;

import java.util.List;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;
import angtrim.com.fivestarslibrary.ReviewListener;

public class FoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
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

    private ListView lvFoodList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food, container, false);
        lvFoodList = (ListView) view.findViewById(R.id.lvFoodList);
        FoodDB.prepareData();

        lvFoodList.setAdapter(new FoodListViewAdapter(FoodDB.getFoodList(), getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    /**
     *
     */
    private class FoodListViewAdapter extends BaseAdapter {

        /**
         * ListView에 세팅할 Item 정보들
         */
        private List foodList;

        /**
         * ListView에 Item을 세팅할 요청자의 정보가 들어감
         */
        private Context context;

        /**
         * 생성자
         *
         * @param foodList
         * @param context
         */
        public FoodListViewAdapter(List foodList, Context context) {
            this.foodList = foodList;
            this.context = context;
        }

        /**
         * ListView에 세팅할 아이템의 갯수
         *
         * @return
         */
        @Override
        public int getCount() {
            return foodList.size();
        }

        /**
         * position 번째 Item 정보를 가져옴
         *
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return foodList.get(position);
        }

        /**
         * 아이템의 index를 가져옴
         * Item index == position
         *
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
         *
         * @param position    : 현재 보여질 아이템의 인덱스, 0 ~ getCount() 까지 증가
         * @param convertView : ListView의 Item Cell(한 칸) 객체를 가져옴
         * @param parent      : ListView
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ItemHolder holder = null;

            /**
             * 가장 간단한 방법
             * 사용자가 처음으로 Flicking을 할 때, 아래쪽에 만들어지는 Cell(한 칸)은 Null이다.
             */
            if (convertView == null) {

                // Item Cell에 Layout을 적용시킬 Inflater 객체를 생성한다.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Item Cell에 Layout을 적용시킨다.
                // 실제 객체는 이곳에 있다.
                convertView = inflater.inflate(R.layout.food_item_list, parent, false);
                holder = new ItemHolder();
            } else {
                holder = (ItemHolder) convertView.getTag();
            }

            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvMenu = (TextView) convertView.findViewById(R.id.tvMenu);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);

            final FoodVO food = (FoodVO) getItem(position);
            holder.image.setImageResource(food.getSrc());
            holder.ratingBar.setRating(food.getRating());
            holder.tvTitle.setText(food.getTitle());
            holder.tvMenu.setText(food.getMenu());
            holder.tvLocation.setText(food.getLocation());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(v.getContext(), food.getRating() + "점", Toast.LENGTH_SHORT).show();
                    FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(v.getContext(),"angelo.gallarello@gmail.com");
                    fiveStarsDialog.setRateText(food.getTitle())
                            .setTitle("평점을 입력하세요.")
                            .setForceMode(false)
                            .setUpperBound(2) // Market opened if a rating >= 2 is selected
                            .setNegativeReviewListener(new NegativeReviewListener() {
                                @Override
                                public void onNegativeReview(int i) {
                                    Toast.makeText(getContext(), "취소했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }) // OVERRIDE mail intent for negative review
                            .setReviewListener(new ReviewListener() {
                                @Override
                                public void onReview(int i) {
                                    Toast.makeText(getContext(), "평점이 반영되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }) // Used to listen for reviews (if you want to track them )
                            .showAfter(0);
                }
            });

            convertView.setTag(holder);

            return convertView;

        }

        private class ItemHolder {
            public ImageView image;
            public RatingBar ratingBar;
            public TextView tvTitle;
            public TextView tvMenu;
            public TextView tvLocation;
        }
    }
}
