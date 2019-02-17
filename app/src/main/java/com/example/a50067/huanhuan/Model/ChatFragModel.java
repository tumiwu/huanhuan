package com.example.a50067.huanhuan.Model;

import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshReplyItemLayoutListener;

public class ChatFragModel implements IChatFragModel {
    @Override
    public void getReplyItemRecViewData(OnSwipeRefreshReplyItemLayoutListener listener) {
        //从数据库获取数据，如果数据大小不变则不刷新
    }
}
