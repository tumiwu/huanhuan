package com.example.a50067.huanhuan.Presenter;

import android.os.Handler;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Entity.ReplyItem;
import com.example.a50067.huanhuan.Fragment.IChatFragment;
import com.example.a50067.huanhuan.Model.ChatFragModel;
import com.example.a50067.huanhuan.Model.IChatFragModel;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshLayoutListener;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshReplyItemLayoutListener;

import java.util.List;

public class ChatFragPresenter {
    private IChatFragModel chatFragModel;
    private IChatFragment chatFragment;
    private Handler mHandler = new Handler();

    public ChatFragPresenter(IChatFragment chatFragment) {
        this.chatFragment = chatFragment;
        chatFragModel=new ChatFragModel();
    }

    public void updateRecViewData(){
        // mainFragment.updateCommodityAdapterData(mainFragModel.getComRecViewData());
        chatFragModel.getReplyItemRecViewData(new OnSwipeRefreshReplyItemLayoutListener() {
            @Override
            public void onRefreshSuccess(List<ReplyItem> replyItemList) {
                mHandler.post(()->{
                    chatFragment.updateReplyAdapterData(replyItemList);
                });

            /*    mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/
            }

            @Override
            public void onRefreshFailed() {

            }
        });
    }
}
