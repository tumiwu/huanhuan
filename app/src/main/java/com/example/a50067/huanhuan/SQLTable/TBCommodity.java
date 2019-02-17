package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import java.util.Date;


/**
 * Created by 50067 on 2018/6/14.
 */

public class TBCommodity  extends LitePalSupport {
    private int id;
    private int userId;
    private byte[] cImage;
    private String cName;
    private String cCategory;
    private Date cUploadDate;     //上传日期
    private String cPrice;
    private String cDetails;
    private int cExchangeable;
    private int cDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int uId) {
        this.userId = uId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCategory() {
        return cCategory;
    }

    public void setcCategory(String cCategory) {
        this.cCategory = cCategory;
    }

    public String getcPrice() {
        return cPrice;
    }

    public void setcPrice(String cPrice) {
        this.cPrice = cPrice;
    }

    public String getcDetails() {
        return cDetails;
    }

    public void setcDetails(String cDetails) {
        this.cDetails = cDetails;
    }

    public int getcExchangeable() {     //默认0：可换 1：不可换
        return cExchangeable;
    }

    public void setcExchangeable(int cExchangeable) {
        this.cExchangeable = cExchangeable;
    }

    public int getcDelete() {
        return cDelete;
    }

    public void setcDelete(int cDelete) {
        this.cDelete = cDelete;
    }

    public byte[] getcImage() {
        return cImage;
    }

    public void setcImage(byte[] cImage) {
        this.cImage = cImage;
    }

    public Date getcUploadDate() {
        return cUploadDate;
    }

    public void setcUploadDate(Date cUploadDate) {
        this.cUploadDate = cUploadDate;
    }
}
