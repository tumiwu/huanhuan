package com.example.a50067.huanhuan.Model;

import android.graphics.Bitmap;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;

/**
 * Created by 50067 on 2018/5/30.
 */

public interface IPublishComModel {
    void saveComMsg(String cName, String cPrice, String cDetails, String cExchangeable, Bitmap bitmap, OnInsertSQLListener listener);
}
