package com.webber.mcorelibspace;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.utiles.Lg;

/**
 * Created by mxh on 2016/11/23.
 * Describe：
 */

public abstract class NActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 設置導航欄顏色
     */
    protected void setStateBarStyle(Drawable stateBarStyle) {
        Lg.print(TAG, "currentVersion:" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);

            //First translucent status bar.
            // window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = STATUS_BAR_HEIGHT;
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
                if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                    //不预留系统空间
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    lp.topMargin += statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }

            View statusBarView = mContentView.getChildAt(0);
            if (statusBarView != null && statusBarView.getLayoutParams() != null
                    && statusBarView.getLayoutParams().height == statusBarHeight) {
                //避免重复调用时多次添加 View
                statusBarView.setBackground(stateBarStyle);
                return;
            }
            statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBarView.setBackground(stateBarStyle);
            //向 ContentView 中添加假 View
            mContentView.addView(statusBarView, 0, lp);
        }
    }
}
