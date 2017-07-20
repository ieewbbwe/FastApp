package com.webber.mcorelibspace.demo.core.dagger;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.BaseActivity;
import com.webber.mcorelibspace.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

@Route(path = "/router/dagger")
public class Dagger2Activity extends BaseActivity implements Dagger2Contract.View {

    @Bind(R.id.m_username_et)
    EditText mUsernameEt;
    @Bind(R.id.m_psw_et)
    EditText mPswEt;
    @Bind(R.id.m_login_bt)
    Button mLoginBt;
    @Bind(R.id.m_login_msg_tv)
    TextView mLoginMsgTv;

    @Inject
    Dagger2Presenter dagger2Presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        //dagger2Presenter = new Dagger2Presenter(this);

    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        mLoginBt.setOnClickListener(v -> {
            dagger2Presenter.login(mUsernameEt.getText().toString(), mPswEt.getText().toString());
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    @Override
    public void showMessage(String msg) {
        mLoginMsgTv.setText(msg);
    }

}
