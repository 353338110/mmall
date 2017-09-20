package com.mmall.pojo;

import java.util.Date;

public class FileBean {
    private String id;

    private String userId;

    private String relationId;

    private Integer type;

    private String url;

    private Date createTime;

    private Date updateTime;

    public FileBean(String id, String userId, String relationId, Integer type, String url, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.relationId = relationId;
        this.type = type;
        this.url = url;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public FileBean() {
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

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
}