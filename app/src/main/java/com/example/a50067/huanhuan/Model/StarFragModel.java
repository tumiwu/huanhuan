package com.example.a50067.huanhuan.Model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Fragment.StarFragment;
import com.example.a50067.huanhuan.Model.ModelListener.OnSwipeRefreshLayoutListener;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBStar;
import com.example.a50067.huanhuan.SQLTable.TBUser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 50067 on 2018/6/11.
 */

public class StarFragModel implements IStarFragModel{
    private String TAG="STAR frag model";
    List<Commodity> starComList;
    int i=0;
//    @Override
//    public void getStarComRecViewData(OnSwipeRefreshLayoutListener listener) {
//
//        //从数据库读数据，如果删除标记为1 则不添加到List中
//        starComList=new ArrayList<>();
//        List<TBStar> tbStars=LitePal.findAll(TBStar.class);
//        if(tbStars.size()==0){
//            listener.refreshFailed("收藏夹为空");
//        }else {
//            try {
//dsasd
//                for (TBStar tbStar : tbStars) {
//                    if (tbStar.getStarDelete()==0){                     //未被标记删除
//                        if(tbStar.getUserId()==MyApplication.getUserId()) {     //判断是否是自己的收藏
//                            int comId = tbStar.getCommodityId();
//                            TBCommodity commodity = LitePal.find(TBCommodity.class, comId);
//                            int uId = commodity.getUserId();
//                            TBUser user1 = LitePal.find(TBUser.class, uId);
//                            Commodity com = new Commodity(commodity.getId(), commodity.getcImage(), commodity.getcPrice(), user1.getuName(), commodity.getcName());
//                            starComList.add(com);
//                        }
//                    }else {
//
//                    }
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            listener.refreshSuccess(starComList);
//        }
//        //List<TBCommodity> commodities= LitePal.findAll(TBCommodity.class);
//
//    }
    @Override
    public void getStarComRecViewData(OnSwipeRefreshLayoutListener listener,Handler handler){

        starComList=new ArrayList<>();
        BmobQuery<TBStar> tbStarBmobQuery=new BmobQuery<>();
        tbStarBmobQuery.addWhereEqualTo("userId",MyApplication.getUserObjectId());  //TBSTAR中,收藏者ID是自己的才展示
        tbStarBmobQuery.findObjects(new FindListener<TBStar>(){
            @Override
            public void done(List<TBStar> list, BmobException e) {
                BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
                if(e==null&&list.size()!=0){
                    for (TBStar star:list ) {

                        tbCommodityBmobQuery.getObject(star.getCommodityId(), new QueryListener<TBCommodity>() {
                            @Override
                            public void done(TBCommodity tbCommodity, BmobException e) {
//                                Commodity com = new Commodity(tbCommodity.getObjectId(), tbCommodity.getcImage(), tbCommodity.getcPrice(), tbCommodity.getcName());
                                Commodity com1=new Commodity();
                                com1.setcId(tbCommodity.getObjectId());
                                com1.setcImage(tbCommodity.getcImage());
                                com1.setcPrice(tbCommodity.getcPrice());
                                com1.setcName(tbCommodity.getcName());
                                BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                                tbUserBmobQuery.getObject(tbCommodity.getUserId(), new QueryListener<TBUser>() {
                                    @Override
                                    public void done(TBUser tbUser, BmobException e) {
                                        if(e==null) {
                                            Log.d(TAG, "done: tbuser username " + tbUser.getuName());
                                            com1.setUserName(tbUser.getuName());
                                            starComList.add(com1);
                                            listener.refreshSuccess(starComList);
                                        }
                                    }
                                });


                            }
                        });

                    }
//                    Message message=Message.obtain();
//                    message.obj=starComList;
//                    message.what=111;
//                    handler.sendMessage(message);
                }else {
//                    Log.d(TAG, "done: e.print "+e.toString());

                }
//            listener.refreshSuccess(starComList);
            }
        });

    }

  /*  @Override
    public void getStarComRecViewData(OnSwipeRefreshLayoutListener listener) {
        BmobQuery<TBStar> tbStarBmobQuery=new BmobQuery<>();
        tbStarBmobQuery.addWhereEqualTo("userId",MyApplication.getUserObjectId());
        tbStarBmobQuery.findObjects(new FindListener<TBStar>() {
            @Override
            public void done(List<TBStar> list, BmobException e) {          //每一条收藏信息
                for (TBStar star:list ) {
                    if(e==null&&list.size()!=0){
                        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
                        tbCommodityBmobQuery.getObject(star.getCommodityId(), new QueryListener<TBCommodity>() {
                            @Override
                            public void done(TBCommodity tbCommodity, BmobException e) {
                                Commodity com1=new Commodity();
                                com1.setcId(tbCommodity.getObjectId());
                                com1.setcImage(tbCommodity.getcImage());
                                com1.setcPrice(tbCommodity.getcPrice());
                                com1.setcName(tbCommodity.getcName());
                                BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                                tbUserBmobQuery.getObject(tbCommodity.getUserId(), new QueryListener<TBUser>() {
                                    @Override
                                    public void done(TBUser tbUser, BmobException e) {
                                        if(e==null){
                                            com1.setUserName(tbUser.getuName());
                                        }
                                    }
                                });
                                starComList.add(com1);
                            }
                        });
                        listener.refreshSuccess(starComList);
                    }


                }


            }
        });
    }*/

}
