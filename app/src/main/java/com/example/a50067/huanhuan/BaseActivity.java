package com.example.a50067.huanhuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public abstract int getRootLayoutId();
    protected abstract void initListener();
    protected abstract void initView();
    //是否显示标题栏
    private boolean isShowTitle=false;
    //是否显示状态栏
    private boolean isShowState=false;
    protected final String TAG=this.getClass().getSimpleName();
    protected final SparseArray<View> mViews = new SparseArray<View>();

    protected View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isShowState) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //初始化布局


    }
    /*
   * 初始化布局控件
   * */

     /*
    * 是否设置标题
    * */
    public void setNOTitle(boolean isshow){
        isShowTitle=isshow;
    }
    /*
   * 是否显示状态栏
   * */
    public void setState(boolean isshow){
        isShowState=isshow;
    }
     /*
    * Toast.LENGTH_LONG
    * */
    public void toastShort(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }
}
