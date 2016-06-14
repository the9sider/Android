package com.ktds.cocomo.customlistview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cocomo.customlistview.facebook.Facebook;
import com.restfb.types.Post;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ListView lvArticleList;
    private Facebook facebook;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        // this : 현재 Activity의 context
        facebook = new Facebook(this);
        facebook.auth();

        lvArticleList = (ListView) findViewById(R.id.lvArticleList);
    }

    public void setTimeline() {
        if (facebook.isLogin()) {
            // timeline 가져오기
            facebook.getTimeLine(new Facebook.TimelineSerializable() {
                @Override
                public void serialize(final List<Post> posts) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvArticleList.setAdapter(new ArticleListViewAdapter(MainActivity.this, posts));
                        }
                    });
                }
            });
        }
    }

    // Ctrl + i
    private class ArticleListViewAdapter extends BaseAdapter {

        /**
         * ListView에 세팅할 Item 정보들
         */
        private List<Post> articleList;

        private Post article;

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
        public ArticleListViewAdapter(Context context, List<Post> articleList) {
            this.articleList = articleList;
            this.context = context;
        }

        /**
         * ListView에 세팅할 아이템의 갯수
         *
         * @return
         */
        @Override
        public int getCount() {
            return articleList.size();
        }

        /**
         * position 번째 Item 정보를 가져옴
         *
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
             * 가장 효율적인 방법 !!
             * 사용자가 처음으로 Flicking을 할 때, 아래쪽에 만들어지는 Cell(한 칸)은 Null이다.
             */
            if (convertView == null) {

                // Item Cell에 Layout을 적용시킬 Inflater 객체를 생성한다.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                /**
                 * Item Cell에 Layout을 적용시킨다.
                 * 실제 객체는 이곳에 있다.
                 */
                convertView = inflater.inflate(R.layout.list_item, parent, false);

                holder = new ItemHolder();
                holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
                holder.tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);

                /**
                 * 제목을 클릭했을 때의 이벤트
                 * holder.tvSubject.setOnClickListener();
                 *
                 */

                // 항상 convertView는 holder를 등에 업어간다.
                convertView.setTag(holder);
            }

            /**
             * convertView != null 이면 홀더를 꺼낸다.
             */
            else {
                holder = (ItemHolder) convertView.getTag();
            }

            article = (Post) getItem(position);
            if (article.getMessage() != null)
                holder.tvSubject.setText(article.getMessage());
            else if (article.getStory() != null)
                holder.tvSubject.setText(article.getStory());

            holder.tvAuthor.setText(article.getFrom().getName());

            if(article.getLikes() == null )
                holder.tvHitCount.setText("0");
            else
                holder.tvHitCount.setText(article.getLikes().getData().size() + "");

            return convertView;
        }
    }

    /**
     * 홀더
     */
    private class ItemHolder {
        public TextView tvSubject;
        public TextView tvAuthor;
        public TextView tvHitCount;
    }
}
