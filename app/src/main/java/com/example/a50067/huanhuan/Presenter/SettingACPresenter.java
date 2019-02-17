package com.example.a50067.huanhuan.Presenter;

import com.example.a50067.huanhuan.Model.ISettingACModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.Model.SettingACModel;
import com.example.a50067.huanhuan.View.ISettingView;

/**
 * Created by 50067 on 2018/6/13.
 */

public class SettingACPresenter{
    private ISettingView settingView;
    private ISettingACModel settingACModel;

    public SettingACPresenter(ISettingView settingView) {
        this.settingView = settingView;
        settingACModel=new SettingACModel();
    }

    public void saveUserMsg(){
        settingACModel.modelSaveUserMsg(settingView.getBitmap(), settingView.getUserTel(), settingView.getUserAddress(), new OnInsertSQLListener() {
            @Override
            public void insertSuccess() {
                settingView.intentToMainAC();
            }

            @Override
            public void insertFailed() {
                settingView.insertFaild();
            }
        });
    }

}
