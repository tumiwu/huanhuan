package com.example.a50067.huanhuan.Presenter;

import com.example.a50067.huanhuan.Model.CommodityACModel;
import com.example.a50067.huanhuan.Model.ICommodityACModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.View.ICommodityACView;

/**
 * Created by 50067 on 2018/6/13.
 */

public class CommodityACPresenter {
    ICommodityACView commodityACView;
    ICommodityACModel commodityACModel;

    public CommodityACPresenter(ICommodityACView commodityACView) {
        this.commodityACView = commodityACView;
        commodityACModel=new CommodityACModel();
    }

    public void starCom(String comId){
        commodityACModel.modelStarCom(comId, new OnInsertSQLListener() {
            @Override
            public void insertSuccess() {
                commodityACView.starSuccess();
            }

            @Override
            public void insertFailed() {

            }
        });
    }
}
