package com.example.a50067.huanhuan.Entity;

import java.util.Date;

/**
 * created by 50067 on 2018/5/29.
 */

public class Commodity {
    String cId;
    byte[] cImage;
    String cExchangeable;
    String cName;
    String cPrice;
    String cDetails;
    Date cUploadDate;
    String UserName;
    String UserSchool;

    public Commodity(){

    }
    public Commodity(String cId, byte[] cImage, String cExchangeable, String cName, String cPrice, String cDetails, Date cUploadDate, String userName, String userSchool) {
        this.cId = cId;
        this.cImage = cImage;
        this.cExchangeable = cExchangeable;
        this.cName = cName;
        this.cPrice = cPrice;
        this.cDetails = cDetails;
        this.cUploadDate = cUploadDate;
        UserName = userName;
        UserSchool = userSchool;
    }

    public Commodity(String cId,byte[] cImage, String cPrice, String cName) {
        this.cId = cId;
        this.cImage = cImage;
        this.cPrice = cPrice;
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public byte[] getcImage() {
        return cImage;
    }

    public void setcImage(byte[] cImage) {
        this.cImage = cImage;
    }

    public String getcExchangeable() {
        return cExchangeable;
    }

    public void setcExchangeable(String cExchangeable) {
        this.cExchangeable = cExchangeable;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
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

    public Date getcUploadDate() {
        return cUploadDate;
    }

    public void setcUploadDate(Date cUploadDate) {
        this.cUploadDate = cUploadDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserSchool() {
        return UserSchool;
    }

    public void setUserSchool(String userSchool) {
        UserSchool = userSchool;
    }
}