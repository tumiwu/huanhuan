package com.example.a50067.huanhuan.Model.ModelListener;

import com.example.a50067.huanhuan.Entity.Commodity;

import java.util.List;

/**
 * Created by 50067 on 2018/6/10.
 */

public interface OnSwipeRefreshLayoutListener {
    void refreshSuccess(List<Commodity> commodityList);
    void refreshFailed(String failedtext);
}
