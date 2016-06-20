package com.example.kgm.lgtwins.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by KGM on 2016-06-20.
 */
public class BoardDB {

    public static List<ArticleVO> articleList;

    public static void prepareData() {

        articleList = new ArrayList<ArticleVO>();

        for(int i = 0; i < 100; i++) {
            articleList.add(new ArticleVO(
                    "Subject " + i,
                    "Author " + i,
                    new Random().nextInt(100),
                    "Description " + i
            ));
        }
    }

    public static List<ArticleVO> getArticleList() {
        return articleList;
    }
}
