package com.ktds.cocomo.memowithweb.vo;

import java.io.Serializable;

/**
 * Created by 206-032 on 2016-06-21.
 */
public class MemoVo implements Serializable {

    private String memoId;
    private String title;
    private String description;
    private String createdDate;
    private String code;

    public String getMemoId() {
        return memoId;
    }

    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
