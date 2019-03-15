package com.example.a50067.huanhuan.Model;

import android.util.Log;

import com.example.a50067.huanhuan.Model.ModelListener.OnInsertSQLListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.SQLTable.TBStar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 50067 on 2018/6/13.
 */

public class CommodityACModel implements ICommodityACModel {
    @Override
    public void modelStarCom(String comId, OnInsertSQLListener listener) {
        BmobQuery<TBStar> tbStarBmobQuery=new BmobQuery<>();
        tbStarBmobQuery.addWhereEqualTo("userId",MyApplication.getUserObjectId());
        tbStarBmobQuery.addWhereEqualTo("commodityId",comId);
        tbStarBmobQuery.findObjects(new FindListener<TBStar>() {
            @Override
            public void done(List<TBStar> list, BmobException e) {
                if(e==null && list.size()!=0){
                    listener.insertFailed();
                }
                else {
                    TBStar tbStar=new TBStar();
                    tbStar.setUserId(MyApplication.getUserObjectId());
                    tbStar.setCommodityId(comId);
                    tbStar.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            listener.insertSuccess();
                        }
                    });
                }
            }
        });



    }

}
