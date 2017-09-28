package com.mmall.pojo;

/**
 * 返回一个uid，用来验证是否登陆用户
 */
public class UIDUser {
    String uid;
    User user;

    public UIDUser(String uid, User user) {
        this.uid = uid;
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
