package com.webber.mcorelibspace.demo.core;

import android.os.Bundle;
import android.widget.TextView;

import com.android_mobile.core.base.BaseActivity;
import com.webber.mcorelibspace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherDemoActivity extends BaseActivity {

    @BindView(R.id.m_demo_tv)
    TextView mDemoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_demo);
        ButterKnife.bind(this);
        mDemoTv.setText("2222");
    }

    @Override
    protected void initComp() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
