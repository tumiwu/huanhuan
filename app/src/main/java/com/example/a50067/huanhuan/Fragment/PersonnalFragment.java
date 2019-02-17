package com.example.a50067.huanhuan.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Adapter.PersonnalOtherItemsAdapter;
import com.example.a50067.huanhuan.Entity.PersonnalOtherItems;
import com.example.a50067.huanhuan.Presenter.BasePresenter;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.Utility.CircleImageView;
import com.example.a50067.huanhuan.View.OrderActivity;
import com.example.a50067.huanhuan.View.PersonnalOrdersActivity;
import com.example.a50067.huanhuan.View.PublishCommodityActivity;
import com.example.a50067.huanhuan.View.SettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50067 on 2018/5/21.
 */

public class PersonnalFragment extends BaseFragment implements IPersonnalFragment,View.OnClickListener {
    ImageButton mUserComment;
    ImageButton mUserFollows;
    ImageButton mUserFans;
    ListView mOhtersListView;

    private List<PersonnalOtherItems> otherItemsList = new ArrayList<>();
    PersonnalOtherItemsAdapter itemsAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();

    }

    @Override
    protected View initView() {
        View mView = LayoutInflater.from(mContent).inflate(R.layout.main_fragment_personnal, null);
        mUserComment = (ImageButton) mView.findViewById(R.id.personnal_comment);
        mUserFollows = (ImageButton) mView.findViewById(R.id.personnal_follow);
        mUserFans = (ImageButton) mView.findViewById(R.id.personnal_fans);
        mOhtersListView = (ListView) mView.findViewById(R.id.personnal_others);
        mOhtersListView.setAdapter(itemsAdapter);
        return mView;
    }

    @Override
    protected void initListener() {
        mUserComment.setOnClickListener(this);
        mUserFollows.setOnClickListener(this);
        mUserFans.setOnClickListener(this);
        mOhtersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonnalOtherItems items = otherItemsList.get(position);
                Intent intent=new Intent(getActivity(), PersonnalOrdersActivity.class);
                switch (position){
                    case 0:                     //我的发布 (查询commodity,userId为自己)
                        intent.putExtra("position",position);
                        getActivity().startActivity(intent);
                        break;
                    case 1:                     //我卖出的 (查询order,sellerId为自己)
                        intent.putExtra("position",position);
                        getActivity().startActivity(intent);
                        break;
                    case 2:                     //我买到的  (查询order,buyerId为自己)
                        intent.putExtra("position",position);
                        startActivity(intent);
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), SettingActivity.class));
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personnal_comment:
                //查看评论
                break;
            case R.id.personnal_follow:
                //查看关注
                break;
            case R.id.personnal_fans:
                //查看粉丝
                break;

        }
    }

    public void initOtherItemsList() {

        PersonnalOtherItems items1 = new PersonnalOtherItems(getString(R.string.personnal_publish));
        PersonnalOtherItems items2 = new PersonnalOtherItems(getString(R.string.personnal_sold));
        PersonnalOtherItems items3 = new PersonnalOtherItems(getString(R.string.personnal_bought));
        PersonnalOtherItems items4 = new PersonnalOtherItems(getString(R.string.personnal_setting));
        otherItemsList.add(items1);
        otherItemsList.add(items2);
        otherItemsList.add(items3);
        otherItemsList.add(items4);
        itemsAdapter = new PersonnalOtherItemsAdapter(mContent, R.layout.personnal_others_item_layout, otherItemsList);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOtherItemsList();
        Log.d(TAG, "onCreate: ");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
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

}
