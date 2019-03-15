package com.example.a50067.huanhuan;

import android.app.Application;
import android.content.Context;

import com.example.a50067.huanhuan.Entity.User;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;

/**
 * Created by 50067 on 2018/6/15.
 */

public class MyApplication extends Application {
    private static Context context;
    private static String UserObjectId;
    private static String userAccount;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
//        LitePal.initialize(context);
        Bmob.initialize(this, "76d62b1b485b08e64a3ef4db16af7893");
    }
    public static Context getContext(){
        return context;
    }

    public static String getUserObjectId() {
        return UserObjectId;
    }

    public static void setUserObjectId(String userObjectId) {
        UserObjectId = userObjectId;
    }



    public static String getUserAccount() {
        return userAccount;
    }

    public static void setUserAccount(String userAccount) {
        MyApplication.userAccount = userAccount;
    }
}
