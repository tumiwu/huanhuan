package com.example.a50067.huanhuan.Presenter;

import com.example.a50067.huanhuan.Model.IOrderACModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.Model.OrderACModel;
import com.example.a50067.huanhuan.View.IOrderACView;

import java.util.Date;

/**
 * Created by 50067 on 2018/6/16.
 */

public class OrderACPresenter {
    IOrderACView orderACView;
    IOrderACModel orderACModel;

    public OrderACPresenter(IOrderACView orderACView) {
        this.orderACView = orderACView;
        orderACModel=new OrderACModel();
    }

    public void insertOrder(String comId,String sellerId){
        orderACModel.modelInsertOrder(comId, sellerId, new Date(), new OnInsertSQLListener() {
            @Override
            public void insertSuccess() {
                orderACView.intentToMainAC();
            }

            @Override
            public void insertFailed() {
                orderACView.orderFailed();
            }
        });
    }
}
