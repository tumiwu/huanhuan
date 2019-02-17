package com.example.a50067.huanhuan.Presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Fragment.IMainFragment;
import com.example.a50067.huanhuan.Fragment.MainFragment;
import com.example.a50067.huanhuan.Model.IMainFragModel;
import com.example.a50067.huanhuan.Model.MainFragModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshLayoutListener;
import com.example.a50067.huanhuan.R;

import java.util.List;

/**
 * Created by 50067 on 2018/5/23.
 */

public class MainFragPresenter extends BasePresenter{
    String TAG="Main frag P:";
    private IMainFragment mainFragment;
    private IMainFragModel mainFragModel;
    private Handler mHandler = new Handler();

    public MainFragPresenter(MainFragment mainFragment1) {
        mainFragment=mainFragment1;
        mainFragModel=new MainFragModel();
    }

    public void updateBannerData(){

        mainFragment.updateBannerTitle(mainFragModel.getBannerTitle());
        mainFragment.updateBannerImage(mainFragModel.getBannerImage(),mainFragModel.getBannerImageLoader());
    }

    public void updateRecViewData(){
       // mainFragment.updateCommodityAdapterData(mainFragModel.getComRecViewData());
        mainFragModel.getComRecViewData(new OnSwipeRefreshLayoutListener() {
            @Override
            public void refreshSuccess(final List<Commodity> commodityList) {
                mHandler.post(()-> {
                    mainFragment.updateCommodityAdapterData(commodityList);
                        mainFragment.refreshSuccess();
                    Log.d(TAG, "refreshSuccess: 刷新成功 数据："+commodityList.size());

                });

            }

            @Override
            public void refreshFailed(String text) {
                //刷新失败：设想：刷新几秒钟未获取到数据则显示失败，且停止刷新
                mHandler.post(()-> {
                    Log.d(TAG, "refreshFailed: 刷新失败 数据：");
                        mainFragment.refreshFailed(text);
                });

            }
        }, mainFragment.getContext());
    }

}
