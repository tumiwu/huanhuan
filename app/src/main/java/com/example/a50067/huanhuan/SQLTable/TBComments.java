package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBComments  extends BmobObject {   //针对订单的评论
    private int id;
    private int sellerId;
    private int buyerId;
    private int commodityId;
    private int orderId;
    private String comtsContent;
    private int comtsDelete;

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getComtsContent() {
        return comtsContent;
    }

    public void setComtsContent(String comtsContent) {
        this.comtsContent = comtsContent;
    }

    public int getComtsDelete() {
        return comtsDelete;
    }

    public void setComtsDelete(int comtsDelete) {
        this.comtsDelete = comtsDelete;
    }
}
