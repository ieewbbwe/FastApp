package com.webber.mcorelibspace.demo.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android_mobile.core.utiles.Utiles;
import com.webber.mcorelibspace.R;

/**
 * Created by mxh on 2017/6/19.
 * Describe：滑动菜单圆点
 */

public class SlidMenuBar extends FrameLayout {

    private int mDeviceHeight;
    private int mDeviceWidth;
    private int mLimitSize = 10;
    private ImageView imageView;

    public SlidMenuBar(Context context) {
        this(context, null);
    }

    public SlidMenuBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidMenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDeviceHeight = Utiles.getScreenHeight(context);
        mDeviceWidth = Utiles.getScreenWidth(context);
        imageView = new ImageView(context);
        FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float mCurrentX = 0;
                float mCurrentY = 0;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mCurrentX = event.getRawX();
                        mCurrentY = event.getRawY();
                        Log.d("webber", "按下：" + mCurrentX + "," + mCurrentY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float mMoveX = mCurrentX - event.getRawX();
                        float mMoveY = mCurrentY - event.getRawY();
                        mCurrentX = event.getRawX();
                        mCurrentY = event.getRawY();
                        //校验是否符合触发距离
                        if (Math.abs(mMoveX) > mLimitSize || Math.abs(mMoveY) > mLimitSize) {
                            imageView.layout((int) mCurrentX, (int) mCurrentY,
                                    (int) (imageView.getMeasuredWidth() + mCurrentX), (int) (imageView.getMeasuredHeight() + mCurrentY));
                            Log.d("webber", "移动：" + mCurrentX + "," + mCurrentY);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起后修正边界
                        float mUpX = mCurrentX = event.getRawX();
                        float mUpY = mCurrentY = event.getRawY();
                        if (Math.abs(mUpX) > mDeviceWidth) {
                            imageView.layout((mDeviceWidth - imageView.getMeasuredWidth()), (int) mCurrentY,
                                    mDeviceWidth, (int) (imageView.getMeasuredHeight() + mCurrentY));
                        }
                        if (Math.abs(mUpY) > mDeviceHeight) {
                            imageView.layout((int) mCurrentX, mDeviceHeight - imageView.getMeasuredHeight(),
                                    (int) (imageView.getMeasuredWidth() + mCurrentX), mDeviceHeight);
                        }
                        Log.d("webber", "抬起：" + mCurrentX + "," + mCurrentY);
                        break;
                }
                invalidate();
                return true;
            }
        });

        addView(imageView, lp);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("webber", "onLayout：" + left + "," + top);
    }
}
