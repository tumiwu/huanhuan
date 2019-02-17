package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBStar extends LitePalSupport {
    private int id;
    private int userId;
    private int commodityId;
    private int starDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getStarDelete() {
        return starDelete;
    }

    public void setStarDelete(int starDelete) {
        this.starDelete = starDelete;
    }
}
