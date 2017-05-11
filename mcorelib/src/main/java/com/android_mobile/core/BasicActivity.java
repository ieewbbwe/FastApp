package com.android_mobile.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by mxh on 2017/4/26.
 * Describe：
 */

public abstract class BasicActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_frame);

    }
}
