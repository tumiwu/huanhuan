package com.example.a50067.huanhuan.Model;

import android.content.Context;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshLayoutListener;
import com.example.a50067.huanhuan.Utility.MyBannerImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50067 on 2018/5/23.
 */

public interface IMainFragModel {
    ArrayList<String> getBannerImage();
    ArrayList<String> getBannerTitle();
    MyBannerImageLoader getBannerImageLoader();
    void getComRecViewData(OnSwipeRefreshLayoutListener listener, Context mContext);
}
