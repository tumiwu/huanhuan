package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

import cn.bmob.v3.BmobObject;


/**
 * Created by 50067 on 2018/6/14.
 */

public class TBOrder  extends BmobObject {
    private String sellerId;
    private String buyerId;
    private String comId;
    private int orState;
    private Date orEstablishDate;
    private Date orFinishDate;
    private int orDelete;

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

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public int getOrState() {
        return orState;
    }

    public void setOrState(int orState) {
        this.orState = orState;
    }

    public Date getOrEstablishDate() {
        return orEstablishDate;
    }

    public void setOrEstablishDate(Date orEstablishDate) {
        this.orEstablishDate = orEstablishDate;
    }

    public Date getOrFinishDate() {
        return orFinishDate;
    }

    public void setOrFinishDate(Date orFinishDate) {
        this.orFinishDate = orFinishDate;
    }

    public int getOrDelete() {
        return orDelete;
    }

    public void setOrDelete(int orDelete) {
        this.orDelete = orDelete;
    }
}
