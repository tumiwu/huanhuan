package com.example.a50067.huanhuan.Model.ModelListener;

import com.example.a50067.huanhuan.Entity.ReplyItem;

import java.util.List;

public interface OnSwipeRefreshReplyItemLayoutListener {
    void onRefreshSuccess(List<ReplyItem> replyItemList);
    void onRefreshFailed();
}
