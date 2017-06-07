package com.android_mobile.core;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_mobile.core.ui.NavigationBar;
import com.android_mobile.core.utiles.Lg;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by mxh on 2017/6/1.
 * Describe：
 */

public abstract class BasicFragment extends RxFragment implements IBasicCoreMethod {

    private View v;
    private BasicActivity activity;
    private Application application;
    private NavigationBar mNavigationBar;

    protected abstract int create();

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();

    public final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(create(), container, false);
        activity = (BasicActivity) getActivity();
        application = activity.getApplication();
        mNavigationBar = activity.mNavigationBar;
        init();
        return v;
    }

    private void printLog() {
        Lg.print(TAG, Thread.currentThread().getStackTrace()[3]
                .getMethodName());
    }

    protected void init() {
        printLog();
        initComp();
        initListener();
        initData();
    }

    public View findViewById(int id) {
        return v.findViewById(id);
    }

    @Override
    public float getScreenHeight() {
        return activity.getScreenHeight();
    }

    @Override
    public float getScreenWidth() {
        return activity.getScreenWidth();
    }

    @Override
    public float getStatusBarHeight() {
        return activity.getStatusBarHeight();
    }

    @Override
    public float getVirtualBarHeight() {
        return activity.getVirtualBarHeight();
    }

    @Override
    public void displayProgressBar() {
        activity.displayProgressBar();
    }

    @Override
    public void hideProgressBar() {
        activity.hideProgressBar();
    }

    @Override
    public void popActivity() {
        activity.popActivity();
    }

    @Override
    public void popFragment() {
        activity.popFragment();
    }

    @Override
    public void pushActivity(Class<?> clazz) {
        activity.pushActivity(clazz);
    }

    @Override
    public void pushActivity(Intent intent, boolean isFinishSelf) {
        activity.pushActivity(intent, isFinishSelf);
    }

    @Override
    public void pushActivityForResult(Class<?> clazz, int requestCode) {
        activity.pushActivityForResult(clazz, requestCode);
    }

    @Override
    public void pushActivityForResult(Intent intent, int requestCode) {
        activity.pushActivityForResult(intent, requestCode);
    }

    @Override
    public void toast(int resId) {
        activity.toast(resId);
    }

    @Override
    public void toast(String str) {
        activity.toast(str);
    }

    @Override
    public void setTitle(String title) {
        activity.setTitle(title);
    }

    @Override
    public void setTitle(int resId) {
        activity.setTitle(resId);
    }
}
