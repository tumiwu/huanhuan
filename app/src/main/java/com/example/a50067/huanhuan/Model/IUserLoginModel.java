package com.example.a50067.huanhuan.Model;

import android.os.Handler;

import com.example.a50067.huanhuan.Model.ModelListener.OnGetURLListener;

/**
 * Created by 50067 on 2018/5/9.
 */

public interface IUserLoginModel {
    static String TAG="UserLoginModel";
    void login(String account, String password,final String checkcode, Handler handler);
    String getCheckCodeURL(OnGetURLListener onGetURLListener);
}
