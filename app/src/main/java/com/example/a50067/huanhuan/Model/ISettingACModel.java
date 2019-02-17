package com.example.a50067.huanhuan.Model;

import android.graphics.Bitmap;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;

/**
 * Created by 50067 on 2018/6/13.
 */

public interface ISettingACModel {
    void modelSaveUserMsg(Bitmap bitmap, String uTel, String uAddress, OnInsertSQLListener listener);
}
