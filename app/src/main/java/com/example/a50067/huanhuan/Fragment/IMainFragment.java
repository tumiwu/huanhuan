package com.example.a50067.huanhuan.Fragment;

import android.content.Context;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Utility.MyBannerImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50067 on 2018/5/23.
 */

public interface IMainFragment {
    void updateBannerImage(ArrayList<String> arrayList, MyBannerImageLoader bannerImageLoader);
    void updateBannerTitle(ArrayList<String> arrayList);
    void updateCommodityAdapterData(List<Commodity> commodityList);
    void refreshSuccess();
    void refreshFailed(String text);
    Context getContext();
}
