package com.example.a50067.huanhuan.Entity;

import java.util.Date;

public class ReplyItem {
    int reply_id;   //评论id;
    int cId;        //商品id,由此点击进入商品界面

    int reply_type;     //回复类型。类型0：回复商品 ；类型1：回复某个用户的评论
    int replyer_id;     //回复者id
    int toReplyer_id;   //被回复者id (回复类型为商品时，此id为发布商品者id)
    byte[] reply_icon;  //回复者头像
    String reply_name;      //回复者名字
    Date reply_time;        //回复时间
    String reply_content;   //回复内容
    byte[] com_pic;         //商品图片
    String com_detail;      //商品详情

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getReply_type() {
        return reply_type;
    }

    public void setReply_type(int reply_type) {
        this.reply_type = reply_type;
    }

    public int getReplyer_id() {
        return replyer_id;
    }

    public void setReplyer_id(int replyer_id) {
        this.replyer_id = replyer_id;
    }

    public int getToReplyer_id() {
        return toReplyer_id;
    }

    public void setToReplyer_id(int toReplyer_id) {
        this.toReplyer_id = toReplyer_id;
    }

    public byte[] getReply_icon() {
        return reply_icon;
    }

    public void setReply_icon(byte[] reply_icon) {
        this.reply_icon = reply_icon;
    }

    public String getReply_name() {
        return reply_name;
    }

    public void setReply_name(String reply_name) {
        this.reply_name = reply_name;
    }

    public Date getReply_time() {
        return reply_time;
    }

    public void setReply_time(Date reply_time) {
        this.reply_time = reply_time;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public byte[] getCom_pic() {
        return com_pic;
    }

    public void setCom_pic(byte[] com_pic) {
        this.com_pic = com_pic;
    }

    public String getCom_detail() {
        return com_detail;
    }

    public void setCom_detail(String com_detail) {
        this.com_detail = com_detail;
    }
}
