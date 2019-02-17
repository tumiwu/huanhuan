package com.example.a50067.huanhuan.Utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 50067 on 2018/10/5.
 * 初始为不允许滑动的viewpager作为fragment的切换器
 */

public class MyNoSlideViewpager extends ViewPager {
    private boolean enabled;
    public MyNoSlideViewpager(Context context) {
        super(context);
        this.enabled=false;
    }

    public MyNoSlideViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(this.enabled) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        if(this.enabled){
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPageEnabled(boolean enabled){
        this.enabled=enabled;
    }
}
