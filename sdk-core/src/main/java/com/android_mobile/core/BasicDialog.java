package com.android_mobile.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by mxh on 2017/6/1.
 * Describe：
 */

public abstract class BasicDialog extends AppCompatDialog {

    public abstract int onCreate();

    public abstract void onViewCreated(View v);

    private View mContentView;
    private Window mWindow;

    public BasicDialog(Context context) {
        this(context, R.style.MyAlertDialogStyle);
    }

    public BasicDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {

        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        // 初始化View
        mContentView = mLayoutInflater.inflate(onCreate(), null);
        setContentView(mContentView);
        mWindow = getWindow();
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        onViewCreated(mContentView);
    }
    /**
     * 设置软键盘模式
     */
    private void setSoftInputMode(int mode) {
        mWindow.setSoftInputMode(mode);
    }
}
