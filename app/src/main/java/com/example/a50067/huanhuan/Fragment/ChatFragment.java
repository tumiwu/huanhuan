package com.example.a50067.huanhuan.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.a50067.huanhuan.Adapter.ReplyItemAdapter;
import com.example.a50067.huanhuan.Entity.ReplyItem;
import com.example.a50067.huanhuan.Presenter.BasePresenter;
import com.example.a50067.huanhuan.Presenter.ChatFragPresenter;
import com.example.a50067.huanhuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50067 on 2018/5/21.
 */

public class ChatFragment extends BaseFragment implements IChatFragment{
    private static final String TAG = "ChatFragment";
    private RecyclerView mReplyRecView;
    private ChatFragPresenter chatFragPresenter;
    private LinearLayoutManager linearLayoutManager;
    private ReplyItemAdapter replyItemAdapter;
    List<ReplyItem> replyItemList=new ArrayList<>();

    @Override
    protected View initView() {
        View mView= LayoutInflater.from(mContent).inflate(R.layout.main_fragment_chat,null);
        mReplyRecView=(RecyclerView)mView.findViewById(R.id.chat_frag_reply_RecView);
        linearLayoutManager=new LinearLayoutManager(mContent);
        getPresenter();
        setRecView();
        return mView;
    }

    private void getPresenter(){
        chatFragPresenter=new ChatFragPresenter(this);
    }
    @Override
    protected void initListener() {

    }

    public void setRecView(){
        //设置recyclerView的样式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mReplyRecView.setLayoutManager(linearLayoutManager);
        mReplyRecView.setItemAnimator(new DefaultItemAnimator());
        chatFragPresenter.updateRecViewData();
        //设置recyclerView的数据

    }



    @Override
    public void updateReplyAdapterData(List<ReplyItem> replyItem) {
        replyItemAdapter=new ReplyItemAdapter(replyItemList,mContent);
        mReplyRecView.setAdapter(replyItemAdapter);
        replyItemAdapter.notifyDataSetChanged();
    }
}
