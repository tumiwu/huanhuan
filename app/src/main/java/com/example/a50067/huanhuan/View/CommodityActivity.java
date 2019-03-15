package com.example.a50067.huanhuan.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Model.ICommodityACModel;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.Presenter.CommodityACPresenter;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBComComments;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBStar;
import com.example.a50067.huanhuan.SQLTable.TBUser;
import com.example.a50067.huanhuan.Utility.CircleImageView;

import org.litepal.LitePal;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class CommodityActivity extends BaseActivity implements ICommodityACView{

    String TAG="Com AC";
    private CircleImageView ComUserIconImage;
    private TextView ComUserName;
    private TextView ComDate;
    private TextView ComPrice;
    private TextView ComTitle;
    private TextView ComDetails;
    private ImageView ComImage;
    private TextView ComUserSchool;
    private TextView ComExchangeable;

    private TextView TVcommentBtn;
    private TextView TVstarBtn;
    private TextView TVIwantBtn;
    private Toolbar toolbarView;
    private View commentDialogView;
    private EditText commentsEdittext;
    private CommodityACPresenter comPresenter;

    private String comId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        comPresenter=new CommodityACPresenter(this);
        Intent intent=getIntent();
        if(intent!=null){
            Log.d(TAG, "onCreate: comId   "+intent.getIntExtra("comId",9999));
        }

        comId=intent.getStringExtra("comId");
        if(comId==null){
            Log.d(TAG, "onCreate: comId error");
        }else {
            setComAC();
        }


    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_commodity;

    }

    @Override
    protected void initListener() {

        TVcommentBtn.setOnClickListener(this);
        TVstarBtn.setOnClickListener(this);
        TVIwantBtn.setOnClickListener(this);
        ComUserIconImage.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_commodity);
        toolbarView=(Toolbar)findViewById(R.id.toolbar);
        toolbarView.setTitle(getResources().getString(R.string.comAC));
        ComUserIconImage=(CircleImageView)findViewById(R.id.comAC_user_icon);
        ComUserName=(TextView)findViewById(R.id.comAC_user_name);
        ComDate=(TextView)findViewById(R.id.comAC_comDate);
        ComPrice=(TextView)findViewById(R.id.comAC_com_price);
        ComTitle=(TextView)findViewById(R.id.comAC_comTitle);
        ComDetails=(TextView)findViewById(R.id.comAC_comDetails);
        ComImage=(ImageView)findViewById(R.id.comAC_comImage);
        ComUserSchool=(TextView)findViewById(R.id.comAC_user_school);
        ComExchangeable=(TextView)findViewById(R.id.comAC_exchangeable);
        TVcommentBtn=(TextView)findViewById(R.id.comAC_comment_Btn);
        TVstarBtn=(TextView)findViewById(R.id.comAC_starCom_Btn);
        TVIwantBtn=(TextView)findViewById(R.id.comAC_Iwant_Btn);
        commentDialogView=LayoutInflater.from(CommodityActivity.this).inflate(R.layout.commit_dialog_layout,null);
        commentsEdittext=(EditText)commentDialogView.findViewById(R.id.commit_dialog_edittext);  //回复框
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comAC_comment_Btn:
                toastShort("u clicked comment");
                addCommit();
                break;
            case R.id.comAC_starCom_Btn:
                starOrBuyCom(v.getId());
                break;
            case R.id.comAC_Iwant_Btn:
                starOrBuyCom(v.getId());
                //跳转到订单页,完成订单
                break;
            case R.id.comAC_user_icon:

//                TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
                BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
                tbCommodityBmobQuery.getObject(comId, new QueryListener<TBCommodity>() {
                    @Override
                    public void done(TBCommodity tbCommodity, BmobException e) {
                        String uId=tbCommodity.getUserId()+"";
                        Bundle bundle=new Bundle();
                        bundle.putString("userId",uId);
                        openActivity(UserMsgActivity.class,bundle);
                    }
                });
               /* String uId=commodity.getUserId()+"";
                Bundle bundle=new Bundle();
                bundle.putString("userId",uId);
                openActivity(UserMsgActivity.class,bundle);*/
                break;
        }
    }

    public void addCommit(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("回复");
        builder.setView(commentDialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comments=commentsEdittext.getText().toString().trim();
                TBComComments tbComComments=new TBComComments();
                tbComComments.setCcomtsContent(comments);
                tbComComments.setCcomtsType(0);     //回复类型：回复商品
                tbComComments.setCommodityId(comId);
                tbComComments.setUserId(MyApplication.getUserObjectId());      //回复者的id
//                tbComComments.setComtsToUserId();     //回复类型为商品，所以回复对象id为空
                tbComComments.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Log.d(TAG, "onClick: 回复的内容："+comments);
                    }
                });

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.show();

    }

    public void setComAC(){
//        TBCommodity mTBcom=LitePal.find(TBCommodity.class,comId);

        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
        tbCommodityBmobQuery.getObject(comId, new QueryListener<TBCommodity>() {
            @Override
            public void done(TBCommodity tbCommodity, BmobException e) {
                String uId = tbCommodity.getUserId();
                BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                tbUserBmobQuery.getObject(uId, new QueryListener<TBUser>() {
                    @Override
                    public void done(TBUser tbUser, BmobException e) {
                        ComUserName.setText(tbUser.getuName());
                        ComUserSchool.setText(tbUser.getuSchool());
                        if(tbUser.getuIcon()!=null){
                            ComUserIconImage.setImageBitmap(BitmapFactory.decodeByteArray(tbUser.getuIcon(),0,tbUser.getuIcon().length));     //可能会报错
                        }
                        //商品信息
                        ComImage.setImageBitmap(BitmapFactory.decodeByteArray(tbCommodity.getcImage(),0,tbCommodity.getcImage().length));
                        ComDate.setText(tbCommodity.getcUploadDate().toString());
                        ComDetails.setText(tbCommodity.getcDetails());
                        ComTitle.setText(tbCommodity.getcName());
                        ComPrice.setText(tbCommodity.getcPrice());
                        ComExchangeable.setText(tbCommodity.getcExchangeable()==1?getString(R.string.exchange):getString(R.string.noexchange));

                    }
                });
            }
        });
      /*  int uId = mTBcom.getUserId();
        TBUser user1 = LitePal.find(TBUser.class, uId);
        //用户信息
        ComUserName.setText(user1.getuName());
        ComUserSchool.setText(user1.getuSchool());
        if(user1.getuIcon()!=null){
            ComUserIconImage.setImageBitmap(BitmapFactory.decodeByteArray(user1.getuIcon(),0,user1.getuIcon().length));     //可能会报错
        }
        //商品信息
        ComImage.setImageBitmap(BitmapFactory.decodeByteArray(mTBcom.getcImage(),0,mTBcom.getcImage().length));
        ComDate.setText(mTBcom.getcUploadDate().toString());
        ComDetails.setText(mTBcom.getcDetails());
        ComTitle.setText(mTBcom.getcName());
        ComPrice.setText(mTBcom.getcPrice());
        ComExchangeable.setText(mTBcom.getcExchangeable()==1?getString(R.string.exchange):getString(R.string.noexchange));
*/


    }
    public void starOrBuyCom(int click){
        /*
        * 1.不能收藏自己的商品
        * 2.收藏已经收藏的商品，点击不做任何响应
        * 先用comId找出卖家Id(表项为userId)
        * 再判断是否与当前登录的id相同
        * 如果相同，弹出不能收藏，如果不相同，收藏成功*/
       /* TBCommodity commodity=LitePal.find(TBCommodity.class,comId);
        int uId=commodity.getUserId();*/
        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
        tbCommodityBmobQuery.getObject(comId, new QueryListener<TBCommodity>() {
            @Override
            public void done(TBCommodity tbCommodity, BmobException e) {
                String uId=tbCommodity.getUserId();
                switch (click){
                    case R.id.comAC_Iwant_Btn:
                        if(uId==MyApplication.getUserObjectId()){
                            toastShort(getString(R.string.comAC_cantBuy));
                        }else {

                            BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                            tbUserBmobQuery.getObject(MyApplication.getUserObjectId(), new QueryListener<TBUser>() {
                                @Override
                                public void done(TBUser tbUser, BmobException e) {
                                    if(tbUser.getuAddress()==null){       //如果购买前判断地址为空，要先设置
                                        toastShort(getString(R.string.comAC_dontHaveAD));
                                    }else {
                                        Bundle bundle=new Bundle();
                                        bundle.putString("comId",comId);
                                        openActivity(OrderActivity.class,bundle);
                                    }
                                }
                            });


                        }

                        break;
                    case R.id.comAC_starCom_Btn:
                        if(uId==MyApplication.getUserObjectId()){
                            toastShort(getString(R.string.comAC_cantStar));
                        }else {
                            comPresenter.starCom(comId);

                        }

                        break;
                }
            }
        });
        /*switch (click){
            case R.id.comAC_Iwant_Btn:
                if(uId==MyApplication.getUserId()){
                    toastShort(getString(R.string.comAC_cantBuy));
                }else {
                    TBUser buyer=LitePal.find(TBUser.class, MyApplication.getUserId());
                    if(buyer.getuAddress()==null){       //如果地址为空，要先设置
                       toastShort(getString(R.string.comAC_dontHaveAD));
                    }else {
                        Bundle bundle=new Bundle();
                        bundle.putInt("comId",comId);
                        openActivity(OrderActivity.class,bundle);
                    }

                }

                break;
            case R.id.comAC_starCom_Btn:
                if(uId==MyApplication.getUserId()){
                    toastShort(getString(R.string.comAC_cantStar));
                }else {
                    comPresenter.starCom(comId);

                }

                break;
        }*/



    }

    @Override
    public void intentToOrderAC() {
        openActivity(OrderActivity.class);

    }

    @Override
    public void buyComFailed() {
        toastShort(getString(R.string.comAC_buyFailed));
    }

    @Override
    public String getComId() {
        return comId;
    }

    @Override
    public void starSuccess() {
        toastShort(getString(R.string.comAC_starSuccess));
    }
}
