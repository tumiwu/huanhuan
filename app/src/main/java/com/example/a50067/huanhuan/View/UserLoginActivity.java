package com.example.a50067.huanhuan.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Presenter.UserLoginPresenter;
import com.example.a50067.huanhuan.R;

import org.litepal.LitePal;

public class UserLoginActivity extends BaseActivity implements IUserLoginView,View.OnClickListener{
    private AutoCompleteTextView mAccountView;
    private EditText mPasswordView;
    private EditText mCheckCodeView;
    private ImageView mCheckCodeImage;
    private View mProgressView;
    private View mLoginFormView;
    private Button mLoginView;
    private Button mClearView;
    private Toolbar toolbarView;
    private UserLoginPresenter userLoginPresenter;

    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoginPresenter=new UserLoginPresenter(this);
        setNOTitle(true);        //显示标题
        setState(false);        //不全屏
        initView();
        initListener();
        setSupportActionBar(toolbarView);
        toolbarView.setTitle(getResources().getString(R.string.action_sign_in));
        LitePal.getDatabase();
        setCheckCode();
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }


    /*
    * 初始化控件
    * */
    @Override
    public void initView() {
        setContentView(getRootLayoutId());
        mAccountView=(AutoCompleteTextView)findViewById(R.id.user_account);
        mPasswordView=(EditText)findViewById (R.id.user_password);
        mCheckCodeView=(EditText)findViewById(R.id.login_checkcode);
        mCheckCodeImage=(ImageView)findViewById(R.id.login_checkcode_img);
        mProgressView=findViewById(R.id.login_progress); //进度条
        mLoginFormView=findViewById(R.id.user_login_form);    //登陆框
        mLoginView=(Button)findViewById (R.id.sign_in_button);
        mClearView=(Button)findViewById(R.id.login_clear_button);
        toolbarView=(Toolbar)findViewById(R.id.toolbar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
               userLoginPresenter.login();       //目前登陆有问题
//                openActivity(MainActivity.class);       //不用账号直接跳转到主界面
                break;
            case R.id.login_clear_button:
                userLoginPresenter.clear();
                break;
        }
    }

    /*
        * 获取用户账号密码
        * */
    @Override
    public String getUserPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public String getUserAccount() {
        return mAccountView.getText().toString();
    }

    @Override
    public String getCheckCode() {
        return mCheckCodeView.getText().toString();
    }

    @Override
    public void setCheckCode() {
        userLoginPresenter.getCheckCodeUrl();
    }

    @Override
    public void setCheckCodeImg(String checkCodeURL) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(UserLoginActivity.this)
                        .load(checkCodeURL)
                        .into(mCheckCodeImage);
                //test
                Log.d(TAG, "login ac setCheckCodeImg: checkcode v "+checkCodeURL);
            }
        });

    }

    /*
    * 登陆成功，跳转页面
    * */
    @Override
    public void intentToMainAC() {
        openActivity(MainActivity.class);
        this.finish();
        toastShort(getString(R.string.welcome_words));
    }

    /*
    * 登录失败
    * */
    @Override
    public void showLoginFailed() {
        toastShort(getString(R.string.login_failed));
    }

    /*
    * 清除账号、密码*/
    @Override
    public void clearAccount() {
        mPasswordView.setText("");
    }
    @Override
    public void clearPassword() {
        mAccountView.setText("");
    }

   /*
   * 显示或隐藏进度
   * */
    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
    }


    @Override
    protected void initListener() {
        mLoginView.setOnClickListener(this);
        mClearView.setOnClickListener(this);
    }

    @Override
    public void intentToFirstLoginAC() {
        Bundle bundle=new Bundle();
        bundle.putString("user_account",getUserAccount());
        bundle.putString("user_password",getUserPassword());
        Log.d(TAG, "intentToFirstLoginAC: account "+getUserAccount());
        Log.d(TAG, "intentToFirstLoginAC: password "+getUserPassword());
        openActivity(FirstLoginActivity.class,bundle);
        this.finish();
    }

}
