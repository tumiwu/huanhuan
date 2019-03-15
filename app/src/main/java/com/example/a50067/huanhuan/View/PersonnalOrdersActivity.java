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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class PersonnalOrdersActivity extends BaseActivity{
    private static final String TAG = "PersonnalOrdersActivity";
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
        BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
        Log.d(TAG, "onCreate: objectid : "+MyApplication.getUserObjectId());
        tbUserBmobQuery.getObject(MyApplication.getUserObjectId(), new QueryListener<TBUser>() {
            @Override
            public void done(TBUser tbUser, BmobException e) {

                if(e==null){
                    user=tbUser;
                }else {
                    Log.d(TAG, "done: e :"+e.toString());
                }
//                user=tbUser;
            }
        });
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
                BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
                tbCommodityBmobQuery.addWhereEqualTo("userId",MyApplication.getUserObjectId());
                tbCommodityBmobQuery.findObjects(new FindListener<TBCommodity>() {
                    @Override
                    public void done(List<TBCommodity> list, BmobException e) {

                        if(e==null&&list.size()!=0){    //如果查询结果不为空

                            for(TBCommodity com:list){
                                if(com.getcDelete()!=1) {
                                    Commodity c = new Commodity(com.getObjectId(), com.getcImage(), com.getcPrice(), com.getcName());
                                    commodityList.add(c);
                                }

                            }
                            commodityAdapter=new OrderItemAdapter(commodityList,PersonnalOrdersActivity.this,position);
                            recView.setAdapter(commodityAdapter);
                        }
                        else {
                        }

                    }

                });

               /* List<TBCommodity> comList=LitePal.findAll(TBCommodity.class);
                for(TBCommodity com:comList){
                    if(com.getUserId()==MyApplication.getUserId()){
                        if(com.getcDelete()!=1) {
                            Commodity c = new Commodity(com.getId(), com.getcImage(), com.getcPrice(), com.getcName());
                            commodityList.add(c);
                        }
                    }
                }*/
                break;
            case 1:                     //我卖出的 (查询order,sellerId为自己)
                commodityList.clear();
                toolbar.setTitle(getString(R.string.personnal_sold));
                BmobQuery<TBOrder> tbOrderBmobQuery=new BmobQuery<>();
                tbOrderBmobQuery.addWhereEqualTo("sellerId",MyApplication.getUserObjectId());
                tbOrderBmobQuery.findObjects(new FindListener<TBOrder>() {
                    @Override
                    public void done(List<TBOrder> list, BmobException e) {
                        if(e==null&&list.size()!=0){
                            for(TBOrder tbOrder:list){

                                String comId=tbOrder.getComId();
                                BmobQuery<TBCommodity> tbCommodityBmobQuery1=new BmobQuery<>();
                                tbCommodityBmobQuery1.getObject(comId, new QueryListener<TBCommodity>() {
                                    @Override
                                    public void done(TBCommodity com, BmobException e) {
//                                        Commodity c=new Commodity(commodity.getObjectId(),commodity.getcImage(),commodity.getcPrice(),commodity.getcName());
                                        Commodity c = new Commodity(com.getObjectId(), com.getcImage(), com.getcPrice(), com.getcName());
                                        commodityList.add(c);
                                    }
                                });

                            }
                            commodityAdapter=new OrderItemAdapter(commodityList,PersonnalOrdersActivity.this,position);
                            recView.setAdapter(commodityAdapter);
                        }

                    }
                });
               /* List<TBOrder> orderList=LitePal.findAll(TBOrder.class);
                for(TBOrder tbOrder:orderList){
                    if(tbOrder.getSellerId()==MyApplication.getUserId()){
                        int comId=tbOrder.getComId();
                        TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
                        Commodity c=new Commodity(commodity.getId(),commodity.getcImage(),commodity.getcPrice(),user.getuName(),commodity.getcName());
                        commodityList.add(c);
                    }
                }*/
                break;
            case 2:                     //我买到的  (查询order,buyerId为自己)
                commodityList.clear();
                toolbar.setTitle(getString(R.string.personnal_bought));
                BmobQuery<TBOrder> tbOrderBmobQuery1=new BmobQuery<>();
                tbOrderBmobQuery1.addWhereEqualTo("buyerId",MyApplication.getUserObjectId());
                tbOrderBmobQuery1.findObjects(new FindListener<TBOrder>() {
                    @Override
                    public void done(List<TBOrder> list, BmobException e) {
                        if(e==null&&list.size()!=0){
                            for(TBOrder tbOrder:list){
                                String comId=tbOrder.getComId();
                                BmobQuery<TBCommodity> commodityBmobQuery=new BmobQuery<>();
                                commodityBmobQuery.getObject(comId, new QueryListener<TBCommodity>() {
                                    @Override
                                    public void done(TBCommodity commodity, BmobException e) {
                                        Commodity c=new Commodity(commodity.getObjectId(),commodity.getcImage(),commodity.getcPrice(),commodity.getcName());
                                        commodityList.add(c);
                                    }
                                });


                            }
                            Log.d(TAG, "done: order size list +"+list.size());
                            Log.d(TAG, "done: order size "+commodityList.size());
                            commodityAdapter=new OrderItemAdapter(commodityList,PersonnalOrdersActivity.this,position);
                            recView.setAdapter(commodityAdapter);
                        }

                    }
                });

                /*List<TBOrder> orderList2=LitePal.findAll(TBOrder.class);
                for(TBOrder tbOrder:orderList2){
                    if(tbOrder.getBuyerId()==MyApplication.getUserId()){
                        int comId=tbOrder.getComId();
                        TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
                        Commodity c=new Commodity(commodity.getId(),commodity.getcImage(),commodity.getcPrice(),user.getuName(),commodity.getcName());
                        commodityList.add(c);
                    }
                }*/
                break;

            case 3:
                break;
        }
    }
}
