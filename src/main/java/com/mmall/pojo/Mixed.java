package com.mmall.pojo;

import java.util.Date;

public class Mixed {
    private String id;

    private String userId;

    private Date createTime;

    private Date updateTime;

    private String mixedtext;

    public Mixed(String id, String userId, Date createTime, Date updateTime, String mixedtext) {
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.mixedtext = mixedtext;
    }

    public Mixed() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMixedtext() {
        return mixedtext;
    }

    public void setMixedtext(String mixedtext) {
        this.mixedtext = mixedtext == null ? null : mixedtext.trim();
    }
}