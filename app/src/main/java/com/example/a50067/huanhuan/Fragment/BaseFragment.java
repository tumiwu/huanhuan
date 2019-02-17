package com.example.a50067.huanhuan.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a50067.huanhuan.Presenter.BasePresenter;

/**
 * Created by 50067 on 2018/5/21.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContent;
    protected abstract View initView();
    protected abstract void initListener();
    protected String TAG;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent=getContext();
    }
    public void getTAG(){
        TAG=this.getClass().getSimpleName();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void toastShort(String text){
        Toast.makeText(mContent,text,Toast.LENGTH_SHORT).show();
    }


}
