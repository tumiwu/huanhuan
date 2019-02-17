package com.example.a50067.huanhuan.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstLoginActivity extends BaseActivity implements IFirstLoginView{
    private EditText mUserName;
    private EditText mUserTel;
    private EditText mUserSchool;
    private Button mLogin;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        initView();
        initListener();
        setEditTextInhibitInputSpace(mUserName);
        setEditTextInhibitInputSpace(mUserSchool);
        setEditTextInhibitInputSpeChat(mUserName);
        setEditTextInhibitInputSpeChat(mUserSchool);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_first_login;
    }

    @Override
    protected void initListener() {
        mLogin.setOnClickListener(this);
    }

    @Override
    protected void initView() {

        mUserName=(EditText)findViewById(R.id.firstLogin_uName);
        mUserTel=(EditText)findViewById(R.id.firstLogin_uTel);
        mUserSchool=(EditText)findViewById(R.id.firstLogin_uSchool);
        mLogin=(Button) findViewById(R.id.firstLogin_Btn);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("登陆");
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.firstLogin_Btn:
                    if(TextUtils.isEmpty(getUName())||TextUtils.isEmpty(getUTel())||TextUtils.isEmpty(getUSchool())){
                        toastShort(getString(R.string.firstLogin_input_failed));
                    }
                    else {
                        Intent intent=getIntent();
                        Bundle bundle=intent.getExtras();
                        TBUser user=new TBUser();
                        Log.d(TAG, "onClick: get account "+bundle.getString("user_account"));
                        Log.d(TAG, "onClick: get password "+bundle.getString("user_password"));
                        user.setuAccount(bundle.getString("user_account"));
                        user.setuPassword(bundle.getString("user_password"));
                        user.setuName(getUName());
                        user.setuSchool(getUSchool());
                        user.setuTel(getUTel());
                        user.save();
                        //向数据库插入账号，登陆，保存Id
                        MyApplication.setUserId(user.getId());
                        openActivity(MainActivity.class);
                        this.finish();
                    }
                    break;
            }
    }

    @Override
    public String getUName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getUTel() {
        return mUserTel.getText().toString();
    }

    @Override
    public String getUSchool() {
        return mUserSchool.getText().toString();
    }

    /**
     * 禁止EditText输入空格
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" "))return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText){

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
