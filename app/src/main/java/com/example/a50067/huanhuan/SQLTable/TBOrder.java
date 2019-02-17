package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import java.util.Date;


/**
 * Created by 50067 on 2018/6/14.
 */

public class TBOrder  extends LitePalSupport {
    private int id;
    private int sellerId;
    private int buyerId;
    private int comId;
    private int orState;
    private Date orEstablishDate;
    private Date orFinishDate;
    private int orDelete;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
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
