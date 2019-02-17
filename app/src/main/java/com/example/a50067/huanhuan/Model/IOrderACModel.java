package com.example.a50067.huanhuan.Model;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;

import java.util.Date;

/**
 * Created by 50067 on 2018/6/16.
 */

public interface IOrderACModel {
    void modelInsertOrder(int comId, int sellerId, Date date, OnInsertSQLListener listener);

}
