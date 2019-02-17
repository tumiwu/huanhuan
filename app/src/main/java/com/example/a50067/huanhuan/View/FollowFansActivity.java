package com.example.a50067.huanhuan.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.R;

import java.util.List;

public class FollowFansActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> followsfansList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followfans);

    }
}
