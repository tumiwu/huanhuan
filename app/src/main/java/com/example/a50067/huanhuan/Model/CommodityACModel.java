package com.example.a50067.huanhuan.Model;

import android.util.Log;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBStar;

/**
 * Created by 50067 on 2018/6/13.
 */

public class CommodityACModel implements ICommodityACModel {
    @Override
    public void modelStarCom(int comId, OnInsertSQLListener listener) {
        TBStar tbStar=new TBStar();
        tbStar.setUserId(MyApplication.getUserId());
        tbStar.setCommodityId(comId);
        tbStar.save();
        listener.insertSuccess();

    }

}
