package com.android_mobile.core.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.android_mobile.core.BasicActivity;

public abstract class BaseComponent {

    abstract int onCreate();

    abstract void initComp();

    abstract void initListener();

    abstract void initData();

    private BasicActivity activity;
    protected View view;
    private ViewGroup root;
    private View _view;

    public BaseComponent(BasicActivity activity, int resId) {
        this.activity = activity;
        view = LayoutInflater.from(activity).inflate(onCreate(), null);
        this._view = view;
        root = (ViewGroup) activity.findViewById(resId);
        root.addView(view);
        initComp();
        initListener();
        initData();
    }

    public BaseComponent(BasicActivity activity, View v) {
        if (v instanceof ViewGroup) {
            this.activity = activity;
            view = LayoutInflater.from(activity).inflate(onCreate(), null);
            this._view = view;
            root = (ViewGroup) v;
            root.addView(view);
            initComp();
            initListener();
            initData();
        }
    }

    public BaseComponent(BasicActivity activity) {
        this.activity = activity;
        view = LayoutInflater.from(activity).inflate(onCreate(), null);
        this._view = view;
        _view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        initComp();
        initListener();
        initData();
    }

    public View findViewById(int id) {
        return view.findViewById(id);
    }

    public ViewGroup getRoot() {
        return root;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public View getView() {
        return _view;
    }

    private int offset = -1;
    private boolean isDisplay = false;

}
