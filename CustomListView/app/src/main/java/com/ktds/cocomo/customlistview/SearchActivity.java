package com.ktds.cocomo.customlistview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cocomo.customlistview.facebook.Facebook;
import com.restfb.types.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends ActionBarActivity {

    // 검색 결과를 몇개로 제한할건지
    private final int SEPARATE = 100;
    private ListView lvArticleList;
    private Facebook facebook;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handler = new Handler();
        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        // 뒤로가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // 검색어 받아오기
        Intent intent = getIntent();
        final String query = intent.getStringExtra("query");

        // 검색어를 ActionBar에 보여주기
        setTitle("검색어 : " + query);

        // 페이스북 인증하고 그 이후에 타임라인 가져오기기
        facebook = new Facebook(this);
        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setTimeline(query);
            }
        });
    }

    /**
     * Set Timeline
     */
    public void setTimeline(final String query) {
        if (facebook.isLogin()) {
            // timeline 가져오기
            facebook.getTimeLine(new Facebook.TimelineSerializable() {
                @Override
                public void serialize(final List<Post> posts) {

                    int postSize = posts.size();

                    int threadCount = 0;
                    if(postSize > SEPARATE) {
                        threadCount = Math.round(postSize / SEPARATE);
                    }
                    threadCount++;

                    final List<Post> searchPost = new ArrayList<Post>();
                    final BaseAdapter baseAdapter = new ArticleListViewAdapter(SearchActivity.this, searchPost);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvArticleList.setAdapter(baseAdapter);
                        }
                    });

                    for(int i = 0; i < threadCount; i ++ ) {
                        final int startIndex = i * SEPARATE;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Post post = null;

                                for ( int j = startIndex; j < (startIndex + SEPARATE); j++  ) {

                                    try {
                                        post = posts.get(j);
                                    } catch (RuntimeException re) {
                                        break;
                                    }

                                    // 대소문자 구분 없애기 위해 모두 소문자로 바꿔서 비교
                                    if (post.getMessage() != null && post.getMessage().length() > 0) {
                                        if (post.getMessage().toLowerCase().contains(query.toLowerCase())) {
                                            setPost(post, searchPost, baseAdapter);
                                        }
                                    } else if (post.getStory() != null && post.getStory().length() > 0) {
                                        if (post.getStory().toLowerCase().contains(query.toLowerCase())) {
                                            setPost(post, searchPost, baseAdapter);
                                        }
                                    } else if (post.getLink() != null && post.getLink().length() > 0) {
                                        if (post.getLink().toLowerCase().contains(query.toLowerCase())) {
                                            setPost(post, searchPost, baseAdapter);
                                        }
                                    }

                                }

                            }
                        }).start();
                    }
                }
            });
        }
    }

    /**
     * Set Post
     * @param post
     * @param searchPost
     * @param baseAdapter
     */
    private void setPost (Post post, final List<Post> searchPost, final BaseAdapter baseAdapter) {
        searchPost.add(post);
        handler.post(new Runnable() {
            @Override
            public void run() {
                baseAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Article List View Adapter
     */
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
         * Item의 속성에 따라서 보여질 아이템 레이아웃을 정해준다.
         *
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {

            /**
             * 만약, Message가 Null이 아니라면 list_item_message를 보여주고
             * 만약, Story가 Null이 아니라면 list_item_story를 보여주고
             * 만약, link가 Null이 아니라면 list_item_link를 보여준다.
             */
            article = (Post) getItem(position);

            if (article.getMessage() != null && article.getMessage().length() > 0) {
                return 0;
            } else if (article.getStory() != null && article.getStory().length() > 0) {
                return 1;
            } else if (article.getStory() != null && article.getStory().length() > 0) {
                return 2;
            } else {
                return -1;
            }
        }

        public int getLayoutType(int index) {
            if (index == 0) {
                return R.layout.list_item_message;
            } else if (index == 1) {
                return R.layout.list_item_story;
            } else if (index == 2) {
                return R.layout.list_item_link;
            } else {
                return -1;
            }
        }

        /**
         * Item Layout의 개수를 가져온다.
         *
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return 3;
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
            int layoutType = getItemViewType(position);

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
                convertView = inflater.inflate(getLayoutType(layoutType), parent, false);
                holder = new ItemHolder();

                if (layoutType == 0) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
                    holder.tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);

                    convertView.setOnClickListener(clickDetail(article.getId()));

                } else if (layoutType == 1) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                } else if (layoutType == 2) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvSubject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                            startActivity(intent);
                        }
                    });
                }

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
            if (layoutType == 0) {
                holder.tvSubject.setText(article.getMessage());
                holder.tvAuthor.setText(article.getFrom().getName());

                if (article.getLikes() == null) {
                    holder.tvHitCount.setText("0");
                } else {
                    holder.tvHitCount.setText(article.getLikes().getData().size() + "");
                }

                convertView.setOnClickListener(clickDetail(article.getId()));

            } else if (layoutType == 1) {
                holder.tvSubject.setText(article.getStory());
            } else if (layoutType == 2) {
                holder.tvSubject.setText(article.getLink());
            }
            return convertView;
        }
    }

    private View.OnClickListener clickDetail(final String postId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        };
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
