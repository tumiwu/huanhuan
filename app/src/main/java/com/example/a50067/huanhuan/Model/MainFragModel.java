package com.example.a50067.huanhuan.Model;

import android.content.Context;
import android.util.Log;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshLayoutListener;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBOrder;
import com.example.a50067.huanhuan.SQLTable.TBUser;
import com.example.a50067.huanhuan.Utility.MyBannerImageLoader;
import com.example.a50067.huanhuan.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 50067 on 2018/5/23.
 */

public class MainFragModel implements IMainFragModel{
    private ArrayList<String> imageList;
    private ArrayList<String> titleList;
    private List<Commodity> commodityList;
    private String TAG="main frag model";
    @Override
    public ArrayList<String> getBannerImage() {
        imageList=new ArrayList<>();
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        return imageList;
    }

    @Override
    public ArrayList<String> getBannerTitle() {
        titleList=new ArrayList<>();
        titleList.add("advertisement 1");
        titleList.add("advertisement 2");
        titleList.add("advertisement 3");
        titleList.add("advertisement 4");
        return titleList;
    }

    @Override
    public MyBannerImageLoader getBannerImageLoader() {
        return new MyBannerImageLoader();
    }

    @Override
    public void getComRecViewData(final OnSwipeRefreshLayoutListener listener, Context mContext) {
        if(commodityList==null){
        commodityList=new ArrayList<>();
        }

        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
//        tbCommodityBmobQuery.addWhereEqualTo("cDelete","0");
        tbCommodityBmobQuery.findObjects(new FindListener<TBCommodity>() {
            @Override
            public void done(List<TBCommodity> list, BmobException e) {

                if(e==null&&list.size()!=0){
                    try {
                        Log.d(TAG, "getComRecViewData: commodities size " + list.size());
//                Log.d(TAG, "getComRecViewData: orderlist size "+orderList.size());
                        for (TBCommodity tbcommodity : list) {
                            Log.d(TAG, "getComRecViewData: come into here");
                            //先查询订单表里面商品有没有被卖出，有则不添加到list。
                            // 卖出的商品delete为1
                            if (tbcommodity.getcDelete() != 1) {
                                String uId = tbcommodity.getUserId();

                                Log.d(TAG, "done: uId "+uId);

                                BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                                tbUserBmobQuery.addWhereEqualTo("objectId",uId);
                                tbUserBmobQuery.getObject(uId, new QueryListener<TBUser>() {
                                    @Override
                                    public void done(TBUser tbUser, BmobException e) {
                                        Commodity commodity1;
                                        String changeable = tbcommodity.getcExchangeable() == 0 ? mContext.getString(R.string.exchange) : mContext.getString(R.string.noexchange);
                                        Log.d(TAG, "getComRecViewData: exchangable" + changeable);

                                        commodity1=new Commodity();
                                        commodity1.setcId(tbcommodity.getObjectId());
                                        commodity1.setcImage(tbcommodity.getcImage());
                                        commodity1.setcExchangeable(changeable);
                                        commodity1.setcName(tbcommodity.getcName());
                                        commodity1.setcPrice(tbcommodity.getcPrice());
                                        commodity1.setcDetails(tbcommodity.getcDetails());
                                        commodity1.setcUploadDate(tbcommodity.getcUploadDate());
                                        commodity1.setUserName(tbUser.getuName());
                                        commodity1.setUserSchool(tbUser.getuSchool());

//                                        commodity1 = new Commodity(tbcommodity.getObjectId(), tbcommodity.getcImage(), changeable, tbcommodity.getcName(), tbcommodity.getcPrice(), tbcommodity.getcDetails(), tbcommodity.getcUploadDate(), tbUser.getuName(), tbUser.getuSchool());
                                        commodityList.add(commodity1);
                                        Log.d(TAG, "getComRecViewData: after add list size 111" + commodityList.size());
                                        listener.refreshSuccess(commodityList);

                                    }
                                });


                    }
//                            }


                        }

                        Log.d(TAG, "getComRecViewData: after add list size " + commodityList.size());
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                }else{
                    listener.refreshFailed("目前没有商品信息!");
                }
            }
        });

       /* List<TBCommodity> commodities= LitePal.findAll(TBCommodity.class);
//        List<TBOrder> orderList=LitePal.findAll(TBOrder.class);
        if(commodities.size()==0){
            listener.refreshFailed("目前没有商品信息!");
        }else {
            try {
                Log.d(TAG, "getComRecViewData: commodities size "+commodities.size());
//                Log.d(TAG, "getComRecViewData: orderlist size "+orderList.size());
                for (TBCommodity tbcommodity : commodities) {
                    Log.d(TAG, "getComRecViewData: come into here");
                    //先查询订单表里面商品有没有被卖出，有则不添加到list。
//                    if(commodities.size()!=0) {           //if(orderList.size()!=0)
//                        for (TBOrder order : orderList) {
//                            if (order.getComId() == tbcommodity.getId()) {
//
//                            } else {
                    if(tbcommodity.getcDelete()!=1) {
                        int uId = tbcommodity.getUserId();
                        TBUser user1 = LitePal.find(TBUser.class, uId);
//                                未登录的时候发布的商品信息，userid为空，所以首页没有商品信息
                        Commodity commodity1;
                        String changeable=tbcommodity.getcExchangeable()==0?mContext.getString(R.string.exchange):mContext.getString(R.string.noexchange);
                        Log.d(TAG, "getComRecViewData: exchangable"+changeable);
                            commodity1 = new Commodity(tbcommodity.getObjectId(), tbcommodity.getcImage(),changeable, tbcommodity.getcName(), tbcommodity.getcPrice(), tbcommodity.getcDetails(), tbcommodity.getcUploadDate(), user1.getuName(), user1.getuSchool());
                        commodityList.add(commodity1);
                        Log.d(TAG, "getComRecViewData: after add list size " + commodityList.size());
//                            }
//                        }
//                    }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            listener.refreshSuccess(commodityList);
        }
*/

    }


}

