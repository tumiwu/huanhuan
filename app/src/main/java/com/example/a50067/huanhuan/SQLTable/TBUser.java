package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBUser extends BmobObject {
    private String uName;
    private byte[] uIcon;
    private String uAccount;
    private String uPassword;
    private String uSchool;
    private String uTel;
    private String uAddress;
    private int uDelete;

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public byte[] getuIcon() {
        return uIcon;
    }

    public void setuIcon(byte[] uIcon) {
        this.uIcon = uIcon;
    }

    public String getuAccount() {
        return uAccount;
    }

    public void setuAccount(String uAccount) {
        this.uAccount = uAccount;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuSchool() {
        return uSchool;
    }

    public void setuSchool(String uSchool) {
        this.uSchool = uSchool;
    }

    public int getuDelete() {
        return uDelete;
    }

    public void setuDelete(int uDelete) {
        this.uDelete = uDelete;
    }
}
