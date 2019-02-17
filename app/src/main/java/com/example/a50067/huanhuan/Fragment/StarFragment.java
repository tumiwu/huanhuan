package com.example.a50067.huanhuan.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.a50067.huanhuan.Adapter.CommodityAdapter;
import com.example.a50067.huanhuan.Adapter.StarComAdapter;
import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Presenter.BasePresenter;
import com.example.a50067.huanhuan.Presenter.StarFragPresenter;
import com.example.a50067.huanhuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50067 on 2018/5/21.
 */

public class StarFragment extends BaseFragment implements IStarFragment {
    String TAG="star F";
    private RecyclerView mStarComRecView;
    private StarFragPresenter starFragPresenter;
    private LinearLayoutManager linearLayoutManager;
    private StarComAdapter starComAdapter;
    List<Commodity> starComList=new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();


    }

    @Override
    public void onResume() {
        super.onResume();
        starFragPresenter.updateRecViewData();
    }

    @Override
    protected View initView() {
        View mView= LayoutInflater.from(mContent).inflate(R.layout.main_fragment_star,null);
        mStarComRecView=(RecyclerView)mView.findViewById(R.id.StarFrag_RecView);
        linearLayoutManager=new LinearLayoutManager(mContent);
        getPresenter();
        setRecView();
        return mView;
    }
    protected void getPresenter(){
        starFragPresenter=new StarFragPresenter(this);
    }
    @Override
    protected void initListener() {

    }

    public void setRecView(){
        //设置recyclerView的样式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mStarComRecView.setLayoutManager(linearLayoutManager);
        mStarComRecView.setItemAnimator(new DefaultItemAnimator());
        //设置recyclerView的数据
        starFragPresenter.updateRecViewData();
    }
    @Override
    public void updateStarCommodityAdapterData(List<Commodity> commodityList) {
        starComAdapter=new StarComAdapter(commodityList,mContent);
        mStarComRecView.setAdapter(starComAdapter);
        starComAdapter.notifyDataSetChanged();

    }


}
