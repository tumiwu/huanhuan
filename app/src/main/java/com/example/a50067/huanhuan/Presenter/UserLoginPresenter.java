package com.example.a50067.huanhuan.Presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a50067.huanhuan.Model.IUserLoginModel;
import com.example.a50067.huanhuan.Model.UserLoginModel;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBUser;
import com.example.a50067.huanhuan.View.IUserLoginView;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

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
    * 用户登录
    * */

    public void login(){
        createHandler();
        userLoginView.showLoading();

        userLoginModel.login(userLoginView.getUserAccount(),userLoginView.getUserPassword(),mHandler);
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
                        if(FirstLogin(userLoginView.getUserAccount())){
                            //如果第一次登陆为true,跳转到设置界面
                            userLoginView.hideLoading();
                            userLoginView.intentToFirstLoginAC();

                        }else {
                            userLoginView.intentToMainAC();
                        }


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
    public Boolean FirstLogin(String account){
        Log.d(TAG, "FirstLogin: account"+account);
        /*查询数据库 查找该账号是否存在*/
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
    }
}
