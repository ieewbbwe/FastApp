package com.webber.mcorelibspace;

import android.os.Bundle;

import com.android_mobile.core.BasicActivity;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
