package com.webber.mcorelibspace.demo.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android_mobile.core.utiles.Lg;
import com.android_mobile.core.utiles.Utiles;
import com.webber.mcorelibspace.R;

/**
 * Created by mxh on 2017/6/19.
 * Describe：滑动菜单圆点
 */

public class SlidMenuBar extends FrameLayout {

    private int mLimitSize = 10;
    private ImageView imageView;
    private int maxW;
    private int maxH;

    public SlidMenuBar(Context context) {
        this(context, null);
    }

    public SlidMenuBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidMenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int mDeviceHeight = Utiles.getScreenHeight(context);
        int mDeviceWidth = Utiles.getScreenWidth(context);
        int mDevicesStatusBar = Utiles.getStatusBarHeight(context);
        imageView = new ImageView(context);
        FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setBackgroundResource(R.mipmap.ic_launcher);

        imageView.setOnTouchListener(new OnTouchListener() {
            float mCurrentX = 0;
            float mCurrentY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mCurrentX = event.getRawX();
                        mCurrentY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float mMoveX = event.getRawX() - mCurrentX;
                        float mMoveY = event.getRawY() - mCurrentY;
                        //校验是否符合触发距离
                        if (Math.abs(mMoveX) > mLimitSize || Math.abs(mMoveY) > mLimitSize) {
                            FrameLayout.LayoutParams lp = (LayoutParams) imageView.getLayoutParams();
                            lp.topMargin += event.getRawY() - mCurrentY;
                            lp.leftMargin += event.getRawX() - mCurrentX;
                            judgeMargin(lp);

                            imageView.setLayoutParams(lp);
                        }
                        mCurrentX = event.getRawX();
                        mCurrentY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起后修正边界
                        float mUpX = mCurrentX = event.getRawX();
                        float mUpY = mCurrentY = event.getRawY();
                        break;
                }
                invalidate();
                return true;
            }
        });
        imageView.measure(0, 0);
        maxW = mDeviceWidth - imageView.getMeasuredWidth();
        maxH = mDeviceHeight - imageView.getMeasuredHeight() - mDevicesStatusBar;
        Lg.print("webber", maxW + "," + maxH);
        addView(imageView, lp);
    }

    private void judgeMargin(LayoutParams lp) {
        if (lp.leftMargin >= maxW) {
            lp.leftMargin = maxW;
        }
        if (lp.leftMargin <= 0) {
            lp.leftMargin = 0;
        }
        if (lp.topMargin >= maxH) {
            lp.topMargin = maxH;
        }
        if (lp.topMargin <= 0) {
            lp.topMargin = 0;
        }
    }

}
