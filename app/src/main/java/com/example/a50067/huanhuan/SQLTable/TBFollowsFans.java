package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBFollowsFans extends BmobObject {
    private String uId;        //关注用户id
    private String fansId;     //粉丝id
    private int ffDelete;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFansId() {
        return fansId;
    }

    public void setFansId(String fansId) {
        this.fansId = fansId;
    }

    public int getFfDelete() {
        return ffDelete;
    }

    public void setFfDelete(int ffDelete) {
        this.ffDelete = ffDelete;
    }
}
