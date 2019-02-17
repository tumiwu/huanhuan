package com.example.a50067.huanhuan;

import android.app.Application;
import android.content.Context;

import com.example.a50067.huanhuan.Entity.User;

import org.litepal.LitePal;

/**
 * Created by 50067 on 2018/6/15.
 */

public class MyApplication extends Application {
    private static Context context;
    private static int UserId;
    private static String userAccount;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
    public static void setUserId(int id){
        UserId=id;
    }
    public static int getUserId(){
        return UserId;
    }

    public static String getUserAccount() {
        return userAccount;
    }

    public static void setUserAccount(String userAccount) {
        MyApplication.userAccount = userAccount;
    }
}
