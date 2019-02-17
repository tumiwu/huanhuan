package com.example.a50067.huanhuan.View;

/**
 * Created by 50067 on 2018/5/8.
 */

public interface IUserLoginView {
    String getUserAccount();
    String getUserPassword();
    void clearAccount();
    void clearPassword();
    void showLoginFailed();
    void intentToMainAC();
    void showLoading();
    void hideLoading();
    void intentToFirstLoginAC();

}
