package com.example.a50067.huanhuan.Model;

import android.os.Handler;
import android.util.Log;

import com.example.a50067.huanhuan.Entity.Commodity;
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

/**
 * Created by 50067 on 2018/6/11.
 */

public class StarFragModel implements IStarFragModel{
    private String TAG="STAR frag model";
    List<Commodity> starComList;
    @Override
    public void getStarComRecViewData(OnSwipeRefreshLayoutListener listener) {

        //从数据库读数据，如果删除标记为1 则不添加到List中
        starComList=new ArrayList<>();
        List<TBStar> tbStars=LitePal.findAll(TBStar.class);
        if(tbStars.size()==0){
            listener.refreshFailed("收藏夹为空");
        }else {
            try {

                for (TBStar tbStar : tbStars) {
                    if (tbStar.getStarDelete()==0){                     //未被标记删除
                        if(tbStar.getUserId()==MyApplication.getUserId()) {     //判断是否是自己的收藏
                            int comId = tbStar.getCommodityId();
                            TBCommodity commodity = LitePal.find(TBCommodity.class, comId);
                            int uId = commodity.getUserId();
                            TBUser user1 = LitePal.find(TBUser.class, uId);
                            Commodity com = new Commodity(commodity.getId(), commodity.getcImage(), commodity.getcPrice(), user1.getuName(), commodity.getcName());
                            starComList.add(com);
                        }
                    }else {

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            listener.refreshSuccess(starComList);
        }
        //List<TBCommodity> commodities= LitePal.findAll(TBCommodity.class);

    }
}
