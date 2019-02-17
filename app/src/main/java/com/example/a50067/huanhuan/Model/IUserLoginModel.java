package com.example.a50067.huanhuan.Model;

import android.os.Handler;

/**
 * Created by 50067 on 2018/5/9.
 */

public interface IUserLoginModel {
    static String TAG="UserLoginModel";
    void login(String account, String password, Handler handler);
}
