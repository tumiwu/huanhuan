package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBStar extends BmobObject {
    private String userId;
    private String commodityId;
    private int starDelete;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getStarDelete() {
        return starDelete;
    }

    public void setStarDelete(int starDelete) {
        this.starDelete = starDelete;
    }
}
