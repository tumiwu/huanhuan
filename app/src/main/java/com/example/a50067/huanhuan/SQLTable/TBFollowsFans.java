package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBFollowsFans  extends LitePalSupport {
    private int id;
    private int uId;        //关注用户id
    private int fansId;     //粉丝id
    private int ffDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getFansId() {
        return fansId;
    }

    public void setFansId(int fansId) {
        this.fansId = fansId;
    }

    public int getFfDelete() {
        return ffDelete;
    }

    public void setFfDelete(int ffDelete) {
        this.ffDelete = ffDelete;
    }
}
