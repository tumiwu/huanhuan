package com.example.a50067.huanhuan.Presenter;

import android.net.Uri;
import android.util.Log;

import com.example.a50067.huanhuan.Model.IPublishComModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.Model.PublishComModel;
import com.example.a50067.huanhuan.View.IPublishcommodityView;
import com.example.a50067.huanhuan.View.PublishCommodityActivity;

import java.sql.Date;

/**
 * Created by 50067 on 2018/5/30.
 */

public class PublishComPresenter {

    private IPublishcommodityView comView;
    private IPublishComModel comModel;

    public PublishComPresenter(PublishCommodityActivity p) {
        Log.d("PubComPresenter", "PublishComPresenter: ");
        comView=p;
        comModel=new PublishComModel();
    }

    public void saveComMsg(){
        /*
        * 获取到输入的商品详情，存储到数据库
        * 标题，详情，价格，照片
        * 发布时间（new Date）,发布人信息*/
        comModel.saveComMsg(comView.getComTitle(),comView.getComPrice(),comView.getComDetails(),comView.getExchangeable(),comView.getBitmap(),new OnInsertSQLListener(){
            @Override
            public void insertSuccess() {
                comView.intentToMainAC();
            }

            @Override
            public void insertFailed() {

            }
        });

    }
}
