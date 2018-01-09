package com.example.android.myapplication.entity;

/**
 * 消息实体类
 * Created by baidan on 2017/12/19.
 */

public class MessageEntity {
    private String title;
    private String time;
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
