package com.example.a50067.huanhuan.View;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.Presenter.OrderACPresenter;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBUser;

import org.litepal.LitePal;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class OrderActivity extends BaseActivity implements IOrderACView{
    String TAG="OrderAC";
    String comId;
    String sellerId;

    private ImageView orderItemImage;
    private TextView orderItemPrice;
    private TextView orderItemTitle;
    private TextView orderItemSeller;
    private TextView buyerTel;
    private Button orderSaveBtn;
    private Toolbar toolbar;

    private OrderACPresenter orPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        comId=bundle.getString("comId");
        Log.d(TAG, "onCreate:comId "+comId);

        initView();
        initListener();

        orPresenter=new OrderACPresenter(this);



    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_order);
        orderItemImage=(ImageView)findViewById(R.id.order_item_image);
        orderItemPrice=(TextView)findViewById(R.id.order_item_cPrice);
        orderItemTitle=(TextView)findViewById(R.id.order_item_cTitle);
        orderItemSeller=(TextView)findViewById(R.id.order_item_seller_buyer);
        buyerTel=(TextView)findViewById(R.id.orderAC_buyerTel);
        orderSaveBtn=(Button)findViewById(R.id.orderAC_saveBtn);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setComMsg();
    }

    @Override
    protected void initListener() {
        orderSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orderAC_saveBtn:
                orPresenter.insertOrder(comId,sellerId);
                break;
        }
    }


    public void setComMsg(){
//        TBCommodity commodity= LitePal.find(TBCommodity.class,comId);
        BmobQuery<TBCommodity> tbCommodityBmobQuery=new BmobQuery<>();
        tbCommodityBmobQuery.getObject(comId, new QueryListener<TBCommodity>() {
            @Override
            public void done(TBCommodity tbCommodity, BmobException e) {
                sellerId = tbCommodity.getUserId();
                BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                tbUserBmobQuery.getObject(sellerId, new QueryListener<TBUser>() {
                    @Override
                    public void done(TBUser seller, BmobException e) {
                        tbUserBmobQuery.getObject(MyApplication.getUserObjectId(), new QueryListener<TBUser>() {
                            @Override
                            public void done(TBUser buyer, BmobException e) {
                                orderItemImage.setImageBitmap(BitmapFactory.decodeByteArray(tbCommodity.getcImage(),0,tbCommodity.getcImage().length));
                                orderItemTitle.setText(tbCommodity.getcName());
                                orderItemPrice.setText(tbCommodity.getcPrice());
                                orderItemSeller.setText(seller.getuName());
                                buyerTel.setText(buyer.getuTel());
                            }
                        });
                    }
                });
            }
        });
     /*   sellerId = commodity.getUserId();
        TBUser seller = LitePal.find(TBUser.class, sellerId);
        TBUser buyer=LitePal.find(TBUser.class, MyApplication.getUserId());
        orderItemImage.setImageBitmap(BitmapFactory.decodeByteArray(commodity.getcImage(),0,commodity.getcImage().length));
        orderItemTitle.setText(commodity.getcName());
        orderItemPrice.setText(commodity.getcPrice());
        orderItemSeller.setText(seller.getuName());
        buyerTel.setText(buyer.getuTel());*/
    }

    @Override
    public void intentToMainAC() {
        openActivity(MainActivity.class);
        Intent intent=new Intent("refresh_recyclerview_data");
        sendBroadcast(intent);
        toastShort(getString(R.string.orderAC_buySuccess));
    }

    @Override
    public void orderFailed() {
        toastShort(getString(R.string.orderAC_failed));
    }
}
