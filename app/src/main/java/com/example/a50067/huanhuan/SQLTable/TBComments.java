package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBComments  extends BmobObject {   //针对订单的评论
    private String sellerId;
    private String buyerId;
    private String commodityId;
    private String orderId;
    private String comtsContent;
    private int comtsDelete;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
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
