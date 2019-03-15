package com.example.a50067.huanhuan.Model;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBOrder;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 50067 on 2018/6/16.
 */

public class OrderACModel implements IOrderACModel {
    @Override
    public void modelInsertOrder(String comId, String sellerId, Date date, OnInsertSQLListener listener) {
        TBOrder order=new TBOrder();
        order.setBuyerId(MyApplication.getUserObjectId());
        order.setComId(comId);
        order.setSellerId(sellerId);
        order.setOrState(0);        //订单状态 0：默认（未完成）1:已完成 2:被取消
        order.setOrEstablishDate(date);
        order.setOrDelete(1);       //下订单后先设置删除标记！
        order.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
//                TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
                BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
                tbCommodityBmobQuery.getObject(comId, new QueryListener<TBCommodity>() {
                    @Override
                    public void done(TBCommodity tbCommodity, BmobException e) {
                        tbCommodity.setcDelete(1);
                        tbCommodity.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                listener.insertSuccess();
                            }
                        });
                    }
                });


            }
        });

    }
}
