package com.example.a50067.huanhuan.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.View.IPublishcommodityView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;


/**
 * Created by 50067 on 2018/5/30.
 */

public class PublishComModel implements IPublishComModel {
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
        tbCommodity.setUserId(MyApplication.getUserId());
        tbCommodity.save();
        listener.insertSuccess();
    }

    private byte[] img(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
