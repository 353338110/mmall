package com.mmall.pojo;

import java.util.Date;

public class Message {
    private String id;

    private String sender;

    private String image;

    private String video;

    private String receiver;

    private Date ceateTime;

    private Date updateTime;

    private Byte readed;

    private String body;

    public Message(String id, String sender, String image, String video, String receiver, Date ceateTime, Date updateTime, Byte readed, String body) {
        this.id = id;
        this.sender = sender;
        this.image = image;
        this.video = video;
        this.receiver = receiver;
        this.ceateTime = ceateTime;
        this.updateTime = updateTime;
        this.readed = readed;
        this.body = body;
    }

    public Message() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public Date getCeateTime() {
        return ceateTime;
    }

    public void setCeateTime(Date ceateTime) {
        this.ceateTime = ceateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getReaded() {
        return readed;
    }

    public void setReaded(Byte readed) {
        this.readed = readed;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }
}