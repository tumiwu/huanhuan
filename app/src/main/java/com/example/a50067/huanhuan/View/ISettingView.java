package com.example.a50067.huanhuan.View;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by 50067 on 2018/6/16.
 */

public interface ISettingView {
    String getUserTel();
    String getUserAddress();
    void intentToMainAC();
    void setImage(Uri uri);
    Bitmap getBitmap();
    void insertFaild();
}
