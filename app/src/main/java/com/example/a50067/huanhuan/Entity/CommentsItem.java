package com.example.a50067.huanhuan.Entity;

public class CommentsItem {
    String uName;
    String toUserName;
    String Contents;

    public CommentsItem(String uName, String contents) {
        this.uName = uName;
        Contents = contents;
    }

    public CommentsItem(String uName, String toUserName, String contents) {
        this.uName = uName;
        this.toUserName = toUserName;
        Contents = contents;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }
}
