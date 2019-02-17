package com.example.a50067.huanhuan.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.a50067.huanhuan.Adapter.CommodityAdapter;
import com.example.a50067.huanhuan.Adapter.OrderItemAdapter;
import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBOrder;
import com.example.a50067.huanhuan.SQLTable.TBUser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PersonnalOrdersActivity extends BaseActivity{

    int position;
    RecyclerView recView;
    private Toolbar toolbar;
    private LinearLayoutManager linearLayoutManager;
    private List<Commodity> commodityList=new ArrayList<>();
    private OrderItemAdapter commodityAdapter;
    private TBUser user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        user=LitePal.find(TBUser.class,MyApplication.getUserId());
        Intent intent=getIntent();
        position=intent.getIntExtra("position",999999);
        if(position==999999){
            Log.d(TAG, "onCreate: per or ac position error");
        }else {
            initComList(position);
        }
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_personnal_orders;
    }
    @Override
    protected void initView() {
        setContentView(getRootLayoutId());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        recView=(RecyclerView)findViewById(R.id.order_Layout_RecView);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(linearLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());




    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onClick(View v) {

    }
    public void initComList(int position){
        switch (position){
            case 0:                     //我的发布 (查询commodity,userId为自己)
                commodityList.clear();
                toolbar.setTitle(getString(R.string.personnal_publish));
                List<TBCommodity> comList=LitePal.findAll(TBCommodity.class);
                for(TBCommodity com:comList){
                    if(com.getUserId()==MyApplication.getUserId()){
                        if(com.getcDelete()!=1) {
                            Commodity c = new Commodity(com.getId(), com.getcImage(), com.getcPrice(), user.getuName(), com.getcName());
                            commodityList.add(c);
                        }
                    }
                }
                break;
            case 1:                     //我卖出的 (查询order,sellerId为自己)
                commodityList.clear();
                toolbar.setTitle(getString(R.string.personnal_sold));
                List<TBOrder> orderList=LitePal.findAll(TBOrder.class);
                for(TBOrder tbOrder:orderList){
                    if(tbOrder.getSellerId()==MyApplication.getUserId()){
                        int comId=tbOrder.getComId();
                        TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
                        Commodity c=new Commodity(commodity.getId(),commodity.getcImage(),commodity.getcPrice(),user.getuName(),commodity.getcName());
                        commodityList.add(c);
                    }
                }
                break;
            case 2:                     //我买到的  (查询order,buyerId为自己)
                commodityList.clear();
                toolbar.setTitle(getString(R.string.personnal_bought));
                List<TBOrder> orderList2=LitePal.findAll(TBOrder.class);
                for(TBOrder tbOrder:orderList2){
                    if(tbOrder.getBuyerId()==MyApplication.getUserId()){
                        int comId=tbOrder.getComId();
                        TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
                        Commodity c=new Commodity(commodity.getId(),commodity.getcImage(),commodity.getcPrice(),user.getuName(),commodity.getcName());
                        commodityList.add(c);
                    }
                }
                break;

            case 3:
                break;
        }

        commodityAdapter=new OrderItemAdapter(commodityList,this,position);
        recView.setAdapter(commodityAdapter);
        //commodityAdapter.notifyDataSetChanged();
    }
}
