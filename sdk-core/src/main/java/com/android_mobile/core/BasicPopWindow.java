package com.android_mobile.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * PopWindow基类
 * Created by mxh on 2016/1/20.
 */
public abstract class BasicPopWindow extends PopupWindow {

    private View mContentView;

    /**
     * 初始化View
     */
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    public abstract void onViewCreated(View view);

    protected final Context ctx;

    public BasicPopWindow(Context context) {
        super(context);
        this.ctx = context;
        initialize();
    }


    private void initialize() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(ctx);
        // 初始化View
        mContentView = onCreateView(mLayoutInflater, null);
        setContentView(mContentView);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //setHeight((int) (mDisplayMetrics.heightPixels * 0.3));
        setFocusable(true);
        setOutsideTouchable(true);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//加上这句话popWindow可显示在keyBoard上
        onViewCreated(mContentView);
    }

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }

}
