package com.example.kgm.lgtwins.board;

import java.io.Serializable;

/**
 * Created by KGM on 2016-06-19.
 */
public class ArticleVO implements Serializable {

    private String subject;
    private String author;
    private int hitCount;
    private String description;

    public ArticleVO(String subject, String author, int hitCount, String description) {
        this.subject = subject;
        this.author = author;
        this.hitCount = hitCount;
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public int getHitCount() {
        return hitCount;
    }

    public String getDescription() {
        return description;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
