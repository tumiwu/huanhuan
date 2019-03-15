package com.example.a50067.huanhuan.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by 50067 on 2018/5/30.
 */

public class PublishComModel implements IPublishComModel {
    private static final String TAG = "PublishComModel";
    TBCommodity tbCommodity=new TBCommodity();
    int e;
    @Override
    public void saveComMsg(String cName, String cPrice, String cDetails, String cExchangeable, Bitmap bitmap, OnInsertSQLListener listener) {
        tbCommodity.setcName(cName);
        tbCommodity.setcUploadDate(new Date());
        tbCommodity.setcPrice(cPrice);
        tbCommodity.setcDetails(cDetails);
        switch (cExchangeable){
            case "不可换":
                e=1;
                default: e=0;
        }
        tbCommodity.setcExchangeable(e);
        tbCommodity.setcImage(img(bitmap));
        Log.d(TAG, "saveComMsg: done:uId + my id : "+MyApplication.getUserObjectId());
        tbCommodity.setUserId(MyApplication.getUserObjectId());
        tbCommodity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e!=null){
                    Log.d(TAG, "done: e "+e.toString());
                }else {
                    listener.insertSuccess();
                }

            }
        });

    }

    private byte[] img(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();


    }



}
