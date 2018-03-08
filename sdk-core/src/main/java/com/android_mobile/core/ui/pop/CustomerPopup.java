package com.android_mobile.core.ui.pop;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.PopupWindow;

/**
 * Created by picher on 2018/2/26.
 * Describe：
 */

public class CustomerPopup implements PopupWindow.OnDismissListener {

    //默认背景透明值
    private static final float DEFAULT_TRANSPARENT_VALUE = 0.7f;
    private Context mContext;
    private PopupWindow mPopWindows;
    private View mContentView;
    private int mLayoutId = 0;
    //寬高
    private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    //動畫
    private int mAnimationStyle = 0;
    //觸摸外部dismiss
    private boolean ismOutsideFocusEnable;
    private boolean mOutsideTouchable;
    private boolean mFocusable;
    //是否背景變暗
    private boolean isBackgroundDim = false;
    private View mDimView;

    private View mAnchor;
    private int mOffsetX;
    private int mOffsetY;
    //透明度
    private int mDimColor = 0x65000000;
    private int mOriginColor = Color.WHITE;
    private boolean isColorful= false;

    public CustomerPopup(Context context) {
        this.mContext = context;
    }

    public <T extends CustomerPopup> T createPopup() {
        if (mPopWindows == null) {
            mPopWindows = new PopupWindow();
        }
        if (mContentView == null) {
            if (mLayoutId != 0) {
                mContentView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            } else {
                throw new IllegalArgumentException("pop content view is null");
            }
        }

        mPopWindows.setContentView(mContentView);
        mPopWindows.setWidth(mWidth);
        mPopWindows.setHeight(mHeight);
        if (mAnimationStyle != 0) {
            mPopWindows.setAnimationStyle(mAnimationStyle);
        }

        if (ismOutsideFocusEnable) {
            mPopWindows.setFocusable(mFocusable);
            mPopWindows.setOutsideTouchable(mOutsideTouchable);
            mPopWindows.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        //設置監聽
        mPopWindows.setOnDismissListener(this);

        return (T) this;
    }

    public <T extends CustomerPopup> T setContentView(View mContentView) {
        this.mContentView = mContentView;
        return (T) this;
    }

    public <T extends CustomerPopup> T setContentView(View mContentView, int width, int height) {
        this.mContentView = mContentView;
        this.mWidth = width;
        this.mHeight = height;
        return (T) this;
    }

    public <T extends CustomerPopup> T setLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
        return (T) this;
    }

    public <T extends CustomerPopup> T setWidth(int mWidth) {
        this.mWidth = mWidth;
        return (T) this;
    }

    public <T extends CustomerPopup> T setHeight(int mHeight) {
        this.mHeight = mHeight;
        return (T) this;
    }

    public <T extends CustomerPopup> T setAnimationStyle(int mAnimationStyle) {
        this.mAnimationStyle = mAnimationStyle;
        return (T) this;
    }

    public <T extends CustomerPopup> T setIsOutsideFocusEnable(boolean ismOutsideFocusEnable) {
        this.ismOutsideFocusEnable = ismOutsideFocusEnable;
        return (T) this;
    }

    public <T extends CustomerPopup> T setOutsideTouchable(boolean mOutsideTouchable) {
        this.mOutsideTouchable = mOutsideTouchable;
        return (T) this;
    }

    public <T extends CustomerPopup> T setFoucusable(boolean focusable) {
        this.mFocusable = focusable;
        return (T) this;
    }

    public <T extends CustomerPopup> T setBackgroundDim(boolean backgroundDim) {
        isBackgroundDim = backgroundDim;
        return (T) this;
    }

    public <T extends CustomerPopup> T setDimView(View dimView) {
        mDimView = dimView;
        return (T) this;
    }

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor,0,0);
    }

    public void showAsDropDown(View anchor, int offsetX, int offsetY) {
        if (mPopWindows != null) {
            this.mAnchor = anchor;
            this.mOffsetX = offsetX;
            this.mOffsetY = offsetY;
            if(isBackgroundDim){
                changeBackgroundDim(true);
            }
            mPopWindows.showAsDropDown(anchor, offsetX, offsetY);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showAsDropDown(View anchor, int offsetX, int offsetY, int gravity) {
        if (mPopWindows != null) {
            this.mAnchor = anchor;
            this.mOffsetX = offsetX;
            this.mOffsetY = offsetY;
            if(isBackgroundDim){
                changeBackgroundDim(true);
            }
            mPopWindows.showAsDropDown(anchor, offsetX, offsetY, gravity);
        }
    }

    private void changeBackgroundDim(boolean isDark) {
        if(mDimView != null){
            if(isColorful){
                showDimColorful(isDark);
            }else{
                showDimOverlay(isDark);
            }
        }
    }

    private void showDimOverlay(boolean isDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            if(isDark){
                ViewGroup parent = (ViewGroup) mDimView;
                Drawable dim = new ColorDrawable(mDimColor);
                dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                dim.setAlpha((int) (255 * DEFAULT_TRANSPARENT_VALUE));
                ViewGroupOverlay overlay = parent.getOverlay();
                overlay.add(dim);
            }else{
                ViewGroup parent = (ViewGroup) mDimView;
                ViewGroupOverlay overlay = parent.getOverlay();
                overlay.clear();
            }
        }
    }

    @Deprecated
    private void showDimColorful(boolean isDark) {
        ValueAnimator colorAnim;
        if(isDark){
            Drawable background = mDimView.getBackground();
            if(background instanceof ColorDrawable){
                mOriginColor = ((ColorDrawable) background).getColor();
            }
            colorAnim = ObjectAnimator.ofInt(mDimView,"backgroundColor", mOriginColor, mDimColor);
        }else{
            colorAnim = ObjectAnimator.ofInt(mDimView,"backgroundColor", mDimColor, mOriginColor);
        }
        colorAnim.setDuration(300);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }

    @Override
    public void onDismiss() {
        //背景變亮
        changeBackgroundDim(false);

        if (mPopWindows != null && mPopWindows.isShowing()) {
            mPopWindows.dismiss();
        }
    }
}
