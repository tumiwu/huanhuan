package com.example.a50067.huanhuan.View;

import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a50067.huanhuan.Adapter.CommodityAdapter;
import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBFollowsFans;
import com.example.a50067.huanhuan.SQLTable.TBUser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class UserMsgActivity extends BaseActivity {
    ImageView userIcon;
    TextView userName;
    TextView userSchool;
    RecyclerView userPublished;
    FloatingActionButton starFloatBtn;
    boolean isStared=false;
    private String userId;
    private TBUser user;
    private List<Commodity> commodityList=new ArrayList<>();
    private CommodityAdapter commodityAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=this.getIntent().getExtras();
        userId=bundle.getString("userId");
        Log.d(TAG, "onCreate: 获取的商品卖家userId = "+bundle.getString("userId"));
        BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
        tbUserBmobQuery.getObject(userId, new QueryListener<TBUser>() {
            @Override
            public void done(TBUser tbUser, BmobException e) {
                user=tbUser;
            }
        });

        Log.d(TAG, "onCreate: valueof id "+Integer.valueOf(userId));

        initView();
        initListener();


    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_user_msg;
    }

    @Override
    protected void initListener() {
        starFloatBtn.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        setContentView(getRootLayoutId());
        userIcon=(ImageView)findViewById(R.id.UserMsgAC_uIcon);
        userName=(TextView)findViewById(R.id.UserMsgAC_uName);
        userSchool=(TextView)findViewById(R.id.UserMsgAC_uSchool);
        userPublished=(RecyclerView)findViewById(R.id.UserMsgAC_RecView);
        starFloatBtn=(FloatingActionButton)findViewById(R.id.UserMsgAC_starUserBar);
        recyclerView=(RecyclerView)findViewById(R.id.UserMsgAC_RecView);
        if(user.getuIcon()!=null) {
            userIcon.setImageBitmap(BitmapFactory.decodeByteArray(user.getuIcon(), 0, user.getuIcon().length));
        }
        userName.setText(user.getuName());
        userSchool.setText(user.getuSchool());
        setStarFloatBtn();
        getUserPublished();
    }
    public void setStarFloatBtn(){
       // 跳转到此界面时，先查询关注表，是否在关注表内。
        //是则设置starFloatBtn颜色，以及设置 isStared
        BmobQuery<TBFollowsFans> tbFollowsFansBmobQuery=new BmobQuery<>();
        tbFollowsFansBmobQuery.addWhereEqualTo("uId",user.getObjectId());
        tbFollowsFansBmobQuery.addWhereEqualTo("fansId",MyApplication.getUserObjectId());
        tbFollowsFansBmobQuery.findObjects(new FindListener<TBFollowsFans>() {
            @Override
            public void done(List<TBFollowsFans> list, BmobException e) {
                if(e==null){        //如果查出来了,则不报错
                    starFloatBtn.setImageResource(R.drawable.star_user);
                    isStared=true;
                    Log.d(TAG, "done: 查询的List大小 "+list.size());
                }
            }
        });

      /* List<TBFollowsFans> followsFansList=LitePal.where("uId=? and fansId=?",user.getId()+"",MyApplication.getUserId()+"").find(TBFollowsFans.class);
       if(followsFansList.get(0)!=null){
           starFloatBtn.setImageResource(R.drawable.star_user);
           isStared=true;
       }*/
    }
public void getUserPublished(){
        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
        tbCommodityBmobQuery.addWhereEqualTo("userId",user.getObjectId());
        tbCommodityBmobQuery.findObjects(new FindListener<TBCommodity>() {
            @Override
            public void done(List<TBCommodity> list, BmobException e) {
                for(TBCommodity com:list){
                        if(com.getcDelete()!=1) {
                            String changable=com.getcExchangeable()==1?"不可换":"可换";
                            Commodity c = new Commodity(com.getObjectId(), com.getcImage(),changable, com.getcName(), com.getcPrice(), com.getcDetails(), com.getcUploadDate(), user.getuName(), user.getuSchool());
//                Commodity c = new Commodity(com.getId(), com.getcImage(), com.getcPrice(), user.getuName(), com.getcName());
                            commodityList.add(c);
                        }

                }
            }
        });
   /* List<TBCommodity> comList=LitePal.findAll(TBCommodity.class);
    for(TBCommodity com:comList){
        if(com.getUserId()==user.getId()){
            if(com.getcDelete()!=1) {
                String changable=com.getcExchangeable()==1?"不可换":"可换";
                Commodity c = new Commodity(com.getId(), com.getcImage(),changable, com.getcName(), com.getcPrice(), com.getcDetails(), com.getcUploadDate(), user.getuName(), user.getuSchool());
//                Commodity c = new Commodity(com.getId(), com.getcImage(), com.getcPrice(), user.getuName(), com.getcName());
                commodityList.add(c);
            }
        }
    }*/
    commodityAdapter=new CommodityAdapter(commodityList,this);
    setRecView();

}
public void setRecView(){
        recyclerView.setAdapter(commodityAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.UserMsgAC_starUserBar:
                //如果商品卖家是自己，则不能关注！
                if(user.getObjectId()!=MyApplication.getUserObjectId()) {
                    //点击关注后，数据库插入数据
                    if (isStared == false) {
                        isStared = true;        //未关注，点击则关注
                    } else {
                        isStared = false;       //已关注，点击则取消关注
                    }
                    if (isStared) {       //加入关注表
                        starFloatBtn.setImageResource(R.drawable.star_user);
                        TBFollowsFans tbFollowsFans = new TBFollowsFans();
                        tbFollowsFans.setuId(user.getObjectId());         //关注者id
                        tbFollowsFans.setFansId(MyApplication.getUserObjectId());     //粉丝id
                        tbFollowsFans.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                Log.d(TAG, "done: 关注成功 "+s);
                            }
                        });
                    } else {

                                            //取消关注
                        starFloatBtn.setImageResource(R.drawable.star_false);
                        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
                       tbCommodityBmobQuery.addWhereEqualTo("uId",user.getObjectId());
                       tbCommodityBmobQuery.addWhereEqualTo("fansId",MyApplication.getUserObjectId());
                       tbCommodityBmobQuery.findObjects(new FindListener<TBCommodity>() {
                           @Override
                           public void done(List<TBCommodity> list, BmobException e) {
                               if(e==null){
                                   list.get(0).setcDelete(1);
                                   list.get(0).save(new SaveListener<String>() {
                                       @Override
                                       public void done(String s, BmobException e) {
                                           Log.d(TAG, "done: 删除成功");
                                       }
                                   });
                               }
                           }
                       });
                        /*List<TBFollowsFans> followsFansList=LitePal.where("uId=? and fansId=?",user.getId()+"",MyApplication.getUserId()+"").find(TBFollowsFans.class);
//                        followsFansList.get(0).setFfDelete(1);  // 设置删除位，
                        followsFansList.get(0).delete();    //删除*/


                    }

                }   else {
                    Toast.makeText(UserMsgActivity.this,"不能关注自己哦！",Toast.LENGTH_SHORT);
                }
                break;
        }
    }
}
