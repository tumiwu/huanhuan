package com.example.a50067.huanhuan.Presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Fragment.IStarFragment;
import com.example.a50067.huanhuan.Model.IStarFragModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnQuerySQLListener;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshLayoutListener;
import com.example.a50067.huanhuan.Model.StarFragModel;

import java.util.List;

/**
 * Created by 50067 on 2018/6/11.
 */

public class StarFragPresenter {
    String TAG="star frag presenter";
    IStarFragModel starFragModel;
    IStarFragment starFragment;
    List<Commodity> commodityList;
    private Handler mHandler = new Handler();

    public StarFragPresenter(IStarFragment starFragment) {
        starFragModel = new StarFragModel();
        this.starFragment = starFragment;
        createHandler();
    }

    public void updateRecViewData(){
        // mainFragment.updateCommodityAdapterData(mainFragModel.getComRecViewData());
        starFragModel.getStarComRecViewData(new OnSwipeRefreshLayoutListener() {
            @Override
            public void refreshSuccess(final List<Commodity> commodityList1) {

                        mHandler.post(()-> {
                            //Log.d(TAG, "star P: refreshSuccess: 输出starComlist 0,1"+commodityList.get(0).getcName()+commodityList.get(1).getcName());
                            starFragment.updateStarCommodityAdapterData(commodityList1);
                            //Log.d(TAG, "refreshSuccess: 返回到star F");
                        });

            }

            @Override
            public void refreshFailed(String text) {
                //刷新失败：设想：刷新几秒钟未获取到数据则显示失败，且停止刷新
                mHandler.post(()->{

                });

            }
        },mHandler);

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


                        break;
                    case 111:
                        commodityList=(List<Commodity>) msg.obj;
                        starFragment.updateStarCommodityAdapterData(commodityList);

                        break;
                }
            }
        };
    }
}
