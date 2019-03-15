package com.example.a50067.huanhuan.Presenter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a50067.huanhuan.Model.IUserLoginModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnGetURLListener;
import com.example.a50067.huanhuan.Model.ModelListener.OnQuerySQLListener;
import com.example.a50067.huanhuan.Model.UserLoginModel;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBUser;
import com.example.a50067.huanhuan.View.IUserLoginView;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 50067 on 2018/5/9.
 */

public class UserLoginPresenter {
    private IUserLoginModel userLoginModel;
    private IUserLoginView userLoginView;
    private Handler mHandler=new Handler();
    private String TAG="userlogin presenter";
    private Boolean isFirstLogin;
    public UserLoginPresenter(IUserLoginView userLoginView){
        this.userLoginView=userLoginView;
        userLoginModel=new UserLoginModel();
    }

    /*
    * 获取验证码*/

    public void getCheckCodeUrl(){
       userLoginModel.getCheckCodeURL(new OnGetURLListener() {
           @Override
           public void getURLSuccess(String url) {
               userLoginView.setCheckCodeImg(url);
               Log.d(TAG, "getURLSuccess: login p url "+url);
           }

           @Override
           public void getURLFailed() {
            // toast获取失败，检查网络
           }
       });
    }


    /*
    * 用户登录
    * */

    public void login(){
        createHandler();
        userLoginView.showLoading();

        userLoginModel.login(userLoginView.getUserAccount(),userLoginView.getUserPassword(),userLoginView.getCheckCode(),mHandler);
    }


    public void clear(){
        userLoginView.clearAccount();
        userLoginView.clearPassword();
    }

    public void createHandler(){
        /*
        * 初始化mHandler
        * */

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 000:

                        /*
                        * 登陆成功
                        * 跳转到主页面，进度条消失
                        * 判断是否第一次登陆 （师大教务在线账号密码登陆）
                        * 第一次：创建用户对象，向数据库插入用户信息---------------------------------
                        * 否：不创建对象
                        * */
                       /* if(FirstLogin(userLoginView.getUserAccount())){
                            //如果第一次登陆为true,跳转到设置界面
                            userLoginView.hideLoading();
                            userLoginView.intentToFirstLoginAC();

                        }else {
                            userLoginView.intentToMainAC();
                        }*/
                       FirstLogin(userLoginView.getUserAccount(), new OnQuerySQLListener() {
                           @Override
                           public void querySuccess() {
                               //不是第一次登陆
                               userLoginView.intentToMainAC();
                           }

                           @Override
                           public void queryFailed() {
                                //是第一次登陆
                               userLoginView.hideLoading();
                               userLoginView.intentToFirstLoginAC();
                           }
                       });


                        Log.d(TAG, "handleMessage:登陆成功 ");
                        break;
                    case 111:

                        /*
                        * 登陆失败
                        * toast错误信息，进度条消失
                        * */
                        userLoginView.hideLoading();
                        userLoginView.showLoginFailed();
                        Log.d(TAG, "handleMessage:登陆失败 ");
                        break;
                }
            }
        };
    }
    /*LitePal的版本*/
   /* public Boolean FirstLogin(String account){
        Log.d(TAG, "FirstLogin: account"+account);
        *//*查询数据库 查找该账号是否存在*//*
        List<TBUser> users=LitePal.findAll(TBUser.class);
        for(TBUser u:users){
            if(u.getuAccount().equals(account)){
                Log.d(TAG, "FirstLogin: find account"+u.getuAccount());
                //如果已经初始化过账号，则该次登陆保留其UserId到application
                MyApplication.setUserId(u.getId());
                MyApplication.setUserAccount(u.getuAccount());
                return false;
            }
        }
        return true;
    }*/

    /*Bmob的版本*/
    public void FirstLogin(String account, OnQuerySQLListener querySQLListener){
        BmobQuery<TBUser> bmobQuery=new BmobQuery<TBUser>();
        bmobQuery.addWhereEqualTo("uAccount",account);
        bmobQuery.findObjects(new FindListener<TBUser>() {
            @Override
            public void done(List<TBUser> list, BmobException e) {
                if(e==null&&list.size()!=0){    //查询成功，不是第一次登陆

//                    Log.d(TAG, "done: e.print "+e.toString());
//                    Log.d(TAG, "done: 查询成功，不是第一登陆");
                    Log.d(TAG, "done: list.size "+list.size());
                    if(list.size()==1){
                        TBUser tbUser=list.get(0);
                        Log.d(TAG, "done:  id "+tbUser.getObjectId());
                        MyApplication.setUserObjectId(tbUser.getObjectId());
                        MyApplication.setUserAccount(tbUser.getuAccount());
                    }
                    querySQLListener.querySuccess();
                }
                else {          //查询失败，是第一次登陆
//                    Log.d(TAG, "done: e.print 2 "+e.toString());
//                    Log.d(TAG, "done: 查询失败，是第一次登陆"+e.getMessage());


                    querySQLListener.queryFailed();

                }
            }
        });
       /* bmobQuery.getObject(account, new QueryListener<TBUser>() {
            @Override
            public void done(TBUser tbUser, BmobException e) {
                if (e==null){

                }   else {
                    Log.d(TAG, "done: 查询失败，是第一次登陆"+e.getMessage());
                    querySQLListener.queryFailed();
                }
            }
        });*/
    }
}
