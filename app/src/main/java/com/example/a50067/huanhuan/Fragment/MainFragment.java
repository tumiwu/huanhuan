package com.example.a50067.huanhuan.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Adapter.CommodityAdapter;
import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Utility.MyBannerImageLoader;
import com.example.a50067.huanhuan.Presenter.MainFragPresenter;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.View.PublishCommodityActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50067 on 2018/5/21.
 */

public class MainFragment extends BaseFragment implements OnBannerListener,IMainFragment,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private String TAG="MainFrag";
    private Banner mBanner;
    private TextView mTextView;
    private MainFragPresenter mainFragPresenter;
    private RecyclerView mComRecView;
    private LinearLayoutManager linearLayoutManager;
    private CommodityAdapter commodityAdapter;
    private FloatingActionButton fAB;
    private SwipeRefreshLayout refreshLayout;
    private ScrollView mainScrollView;
    View mView;
    List<Commodity> commodityList=new ArrayList<>();
    private Handler handler=new Handler();
    boolean isLoading;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        getPresenter();
        registerRefreshRecViewDataBroadCast();
    }


  public void registerRefreshRecViewDataBroadCast(){
        MyRefreshDataBroadcast broadcast=new MyRefreshDataBroadcast();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("refresh_recyclerview_data");
        getActivity().registerReceiver(broadcast,intentFilter);
    }
    @Override
    protected View initView() {
        if(mView!=null){
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }


        mView=LayoutInflater.from(mContent).inflate(R.layout.main_fragment,null);
//        mainScrollView=(ScrollView)getActivity().findViewById(R.id.mainAC_parent_scrollView);
        mBanner=(Banner)mView.findViewById(R.id.main_frag_banner);
        mComRecView=(RecyclerView)mView.findViewById(R.id.Main_Frag_Commodity_RecView);
//        mComRecView.setVisibility(View.GONE); ///////////////////////
        fAB=(FloatingActionButton)mView.findViewById(R.id.addComFAB);
        refreshLayout=(SwipeRefreshLayout)mView.findViewById(R.id.mainFrag_refresh_layout);
        linearLayoutManager=new LinearLayoutManager(mContent);
        getPresenter();
        setmBanner();
        setRecView();
        refreshLayout.post(()->{
                    refreshLayout.setRefreshing(true);
                    onRefresh();
        }
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        commodityList.clear();
                        mainFragPresenter.updateRecViewData();
                    }
                },2000);
            }
        });
        return mView;
    }




    protected void getPresenter() {
        //初始化presenter
        mainFragPresenter=new MainFragPresenter(this);
    }


    public void setmBanner(){
        //设置banner数据
        mainFragPresenter.updateBannerData();
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setDelayTime(3000);
        mBanner.isAutoPlay(true);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this)
                .start();
    }
    public void setRecView(){
        //设置recyclerView的样式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mComRecView.setLayoutManager(linearLayoutManager);
        mComRecView.setItemAnimator(new DefaultItemAnimator());
//        mComRecView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_UP)
//                return false;
//            }
//        });
        //设置recyclerView的数据
//        Log.d(TAG, "setRecView: start refresh recview");
//        mainFragPresenter.updateRecViewData();
//        Log.d(TAG, "setRecView: after refresh recview");
       // commodityAdapter=new CommodityAdapter(commodityList,mContent);
      //  mComRecView.setAdapter(commodityAdapter);

        mComRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged: new state "+newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//                if(lastVisibleItemPosition +1 == commodityAdapter.getItemCount()){
//                    boolean isRefreshing=refreshLayout.isRefreshing();
//                    if(isRefreshing){
//                        commodityAdapter.notifyItemRemoved(commodityAdapter.getItemCount());
//                        return;
//                    }
//                    if(!isLoading){
//                        isLoading=true;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mainFragPresenter.updateRecViewData();
//                                isLoading=false;
//                            }
//                        },1000);
//                    }
//                }
            }
        });

    }
    @Override
    public void OnBannerClick(int position) {
        //banner点击事件
        toastShort("你点击了第"+(position+1)+"张轮播图");
    }


    @Override
    public void updateBannerImage(ArrayList<String> arrayList, MyBannerImageLoader bannerImageLoader) {
        //设置banner图片
        mBanner.setImageLoader(bannerImageLoader);
        mBanner.setImages(arrayList);
    }


    @Override
    public void updateBannerTitle(ArrayList<String> arrayList) {
        //设置banner标题
        mBanner.setBannerTitles(arrayList);
    }
    @Override
    public void updateCommodityAdapterData(List<Commodity> commodityList){
       commodityAdapter=new CommodityAdapter(commodityList,mContent);
        mComRecView.setAdapter(commodityAdapter);
        commodityAdapter.notifyDataSetChanged();
        this.commodityList=commodityList;
        Log.d(TAG, "updateCommodityAdapterData: recview size "+commodityList.size());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addComFAB:
                startActivity(new Intent(getActivity(), PublishCommodityActivity.class));
                break;
        }
    }

    @Override
    protected void initListener() {
        fAB.setOnClickListener(this);
        refreshLayout.setOnClickListener(this);

    }

    @Override
    public void onRefresh() {
        //检测到下拉刷新，P层调用M层刷新数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainFragPresenter.updateRecViewData();
//                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    @Override
    public void refreshSuccess() {
        commodityAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        commodityAdapter.notifyItemRemoved(commodityAdapter.getItemCount());
       // toastShort(getResources().getString(R.string.mainFrag_refresh_success));
    }

    @Override
    public void refreshFailed(String text) {
       toastShort(text);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void getTAG() {
        super.getTAG();
        Log.d(TAG, "getTAG: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(commodityList.size()==0){
//            mainScrollView.requestDisallowInterceptTouchEvent(true);
//        }else {
//            mainScrollView.requestDisallowInterceptTouchEvent(false);
//        }
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    class MyRefreshDataBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            commodityList.clear();
            mainFragPresenter.updateRecViewData();
            Log.d(TAG, "onReceive: 收到添加商品页传来的刷新广播");
        }
    }

}
