package com.example.a50067.huanhuan.View;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a50067.huanhuan.Adapter.MainPagerAdapter;
import com.example.a50067.huanhuan.Adapter.MyNewMainPagerAdapter;
import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Fragment.BaseFragment;
import com.example.a50067.huanhuan.Fragment.ChatFragment;
import com.example.a50067.huanhuan.Fragment.FragmentFactory;
import com.example.a50067.huanhuan.Fragment.MainFragment;
import com.example.a50067.huanhuan.Fragment.PersonnalFragment;
import com.example.a50067.huanhuan.Fragment.StarFragment;
import com.example.a50067.huanhuan.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private Toolbar toolbarView;
    private TabLayout mTabView;
    private ViewPager mViewPager;
    private FragmentManager fragmentManager=getSupportFragmentManager();
    private MyNewMainPagerAdapter newMainPagerAdapter;

    private Button mainBtn;
    private Button chatBtn;
    private Button starBtn;
    private Button personnalBtn;

    private List<Fragment> fragmentList;
    private ChatFragment chatFragment;
    private MainFragment mainFragment;
    private PersonnalFragment personnalFragment;
    private StarFragment starFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNOTitle(false);
        setState(false);

        initView();
        initFragment();


        setSupportActionBar(toolbarView);
        toolbarView.setTitle(getResources().getString(R.string.mainac));
        newMainPagerAdapter=new MyNewMainPagerAdapter(fragmentManager,fragmentList);
        Log.d(TAG, "onCreate: fragment list size "+fragmentList.size());
        mViewPager.setAdapter(newMainPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);
        initListener();
//        mTabView.setupWithViewPager(mViewPager);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initFragment(){
        if(chatFragment==null){
            chatFragment=new ChatFragment();
        }
        if(mainFragment==null){
            mainFragment=new MainFragment();
        }
        if(starFragment==null){
            starFragment=new StarFragment();
        }
        if(personnalFragment==null){
            personnalFragment=new PersonnalFragment();
        }
        if(fragmentList==null) {
            fragmentList = new ArrayList<Fragment>();

            fragmentList.add(mainFragment);
            fragmentList.add(chatFragment);
            fragmentList.add(starFragment);
            fragmentList.add(personnalFragment);
        }
    }
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        toolbarView=(Toolbar)findViewById(R.id.toolbar);
        mViewPager=(ViewPager)findViewById(R.id.main_ViewPager);

//        mTabView=(TabLayout)findViewById(R.id.main_TabLayout);
//        MainPagerAdapter adapter=new MainPagerAdapter(getSupportFragmentManager(),this);
        mainBtn=(Button)findViewById(R.id.main_frag_btn);
        chatBtn=(Button)findViewById(R.id.chat_frag_btn);
        starBtn=(Button)findViewById(R.id.star_frag_btn);
        personnalBtn=(Button)findViewById(R.id.personnal_frag_btn);

        mainBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);
        starBtn.setOnClickListener(this);
        personnalBtn.setOnClickListener(this);

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void toastShort(String text) {
        super.toastShort(text);
    }


    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
//                FragmentTransaction transaction=fragmentManager.beginTransaction();
//                Fragment fragment=FragmentFactory.createFragment(position);
//                transaction.show(fragment);
//                super.onPageSelected(position);
//                mViewPager.setCurrentItem(0);
//                switch (position){
//                    case 0:
//
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_frag_btn:
                mViewPager.setCurrentItem(0);
                mainBtn.setEnabled(false);
                chatBtn.setEnabled(true);
                starBtn.setEnabled(true);
                personnalBtn.setEnabled(true);
                break;
            case R.id.chat_frag_btn:
                mViewPager.setCurrentItem(1);
                mainBtn.setEnabled(true);
                chatBtn.setEnabled(false);
                starBtn.setEnabled(true);
                personnalBtn.setEnabled(true);
                break;
            case R.id.star_frag_btn:
                mViewPager.setCurrentItem(2);
                mainBtn.setEnabled(true);
                chatBtn.setEnabled(true);
                starBtn.setEnabled(false);
                personnalBtn.setEnabled(true);
                break;
            case R.id.personnal_frag_btn:
                mViewPager.setCurrentItem(3);
                mainBtn.setEnabled(true);
                chatBtn.setEnabled(true);
                starBtn.setEnabled(true);
                personnalBtn.setEnabled(false);
                break;
        }
    }


}

