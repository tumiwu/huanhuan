package com.example.a50067.huanhuan.View;

import android.graphics.Bitmap;
import android.net.Uri;

import java.sql.Date;

/**
 * Created by 50067 on 2018/5/30.
 */

public interface IPublishcommodityView {
    String getComTitle();
    String getComDetails();
    String getComPrice();
    void intentToMainAC();
    void setImage(Uri uri);
    String getExchangeable();
    Bitmap getBitmap();

}
