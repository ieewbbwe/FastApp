package com.webber.mcorelibspace.demo.core;

import android.os.Bundle;

import com.android_mobile.core.base.BaseActivity;
import com.webber.mcorelibspace.R;

public class CoreDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_demo);
        mNavigationBar.hidden();
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
