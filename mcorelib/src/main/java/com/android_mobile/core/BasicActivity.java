package com.android_mobile.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;

import com.android_mobile.core.utiles.Lg;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by mxh on 2017/4/26.
 * Describe：
 */

public abstract class BasicActivity extends RxAppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private LayoutInflater layoutInflater;

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        printLog();
        BasicApplication.activityStack.add(this);
        layoutInflater = getLayoutInflater();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_frame);
    }

    private void printLog() {
        Lg.print(TAG, Thread.currentThread().getStackTrace()[3].getMethodName());
    }
}
