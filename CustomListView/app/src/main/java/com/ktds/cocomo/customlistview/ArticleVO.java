package com.ktds.cocomo.customlistview;

import java.io.Serializable;

/**
 * Created by 206-032 on 2016-06-14.
 *
 * Serializable 을 implements 하면
 */
public class ArticleVO implements Serializable {

    private String subject;
    private String author;
    private int hitCoutn;

    public ArticleVO(String subject, String author, int hitCoutn) {
        this.subject = subject;
        this.author = author;
        this.hitCoutn = hitCoutn;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public int getHitCoutn() {
        return hitCoutn;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setHitCoutn(int hitCoutn) {
        this.hitCoutn = hitCoutn;
    }
}
