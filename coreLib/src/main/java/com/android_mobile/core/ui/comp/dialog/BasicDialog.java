package com.android_mobile.core.ui.comp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android_mobile.core.R;

/**
 * Created by mxh on 2016/10/26.
 * Describe：对话框基类
 */
public abstract class BasicDialog extends Dialog {

    private LayoutInflater mLayoutInflater;
    private View mContentView;
    private Window mWindow;
    protected DisplayMetrics mDisplayMetrics;

    public BasicDialog(Context context) {
        this(context, R.style.MyAlertDialogStyle);
    }

    public BasicDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {

        mLayoutInflater = LayoutInflater.from(getContext());
        // 初始化View
        mContentView = mLayoutInflater.inflate(onCreate(), null);
        setContentView(mContentView);
        mWindow = getWindow();
        mDisplayMetrics = new DisplayMetrics();

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        onViewCreated(mContentView);
    }

    /**
     * 初始化View
     */
    protected abstract int onCreate();

    /**
     * 设置软键盘模式
     */
    public abstract void onViewCreated(View v);

    /**
     * 设置软键盘模式
     */
    public void setSoftInputMode(int mode) {
        mWindow.setSoftInputMode(mode);
    }
}
