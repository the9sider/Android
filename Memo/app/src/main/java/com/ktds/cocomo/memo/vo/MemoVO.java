package com.ktds.cocomo.memo.vo;

import java.io.Serializable;

/**
 * Created by 206-032 on 2016-06-20.
 */
public class MemoVO implements Serializable {

    private int _id;
    private String title;
    private String description;
    private String date;
    private long time;

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
