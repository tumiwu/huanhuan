package com.example.a50067.huanhuan.Model;

import android.graphics.Bitmap;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBUser;

import java.io.ByteArrayOutputStream;

/**
 * Created by 50067 on 2018/6/13.
 */

public class SettingACModel implements ISettingACModel {
    TBUser tbUser=new TBUser();
    @Override
    public void modelSaveUserMsg(Bitmap bitmap, String uTel, String uAddress, OnInsertSQLListener listener) {
        tbUser.setuTel(uTel);
        tbUser.setuAddress(uAddress);
        if(bitmap!=null){
            tbUser.setuIcon(img(bitmap));
        }
        tbUser.updateAll("id=?", MyApplication.getUserId()+"");
        listener.insertSuccess();

        //插入失败的情况
    }
    private byte[] img(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
