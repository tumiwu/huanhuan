package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.example.a50067.huanhuan.Fragment.BaseFragment;
import com.example.a50067.huanhuan.Fragment.FragmentFactory;
import com.example.a50067.huanhuan.R;

/**
 * Created by 50067 on 2018/5/21.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    public String[] mTitle;
    private Context mContext;
    public MainPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        mTitle=mContext.getResources().getStringArray(R.array.tab_title);
        this.mContext=mContext;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment= FragmentFactory.createFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4; //首页f数量
    }

}
