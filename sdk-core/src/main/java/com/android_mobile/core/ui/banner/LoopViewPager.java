/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android_mobile.core.ui.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android_mobile.core.R;

import java.util.ArrayList;


/**
 * A ViewPager subclass enabling infinte scrolling of the viewPager elements
 * <p/>
 * When used for paginating views (in opposite to fragments), no code changes
 * should be needed only change xml's from <android.support.v4.view.ViewPager>
 * to <com.imbryk.viewPager.LoopViewPager>
 * <p/>
 * If "blinking" can be seen when paginating to first or last view, simply call
 * seBoundaryCaching( true ), or change DEFAULT_BOUNDARY_CASHING to true
 * <p/>
 * When using a FragmentPagerAdapter or FragmentStatePagerAdapter, additional
 * changes in the adapter must be done. The adapter must be prepared to create 2
 * extra items e.g.:
 * <p/>
 * The original adapter creates 4 items: [0,1,2,3] The modified adapter will
 * have to create 6 items [0,1,2,3,4,5] with mapping
 * realPosition=(position-1)%count [0->3, 1->0, 2->1, 3->2, 4->3, 5->0]
 */
public class LoopViewPager extends ViewPager {

    private static final boolean DEFAULT_BOUNDARY_CASHING = false;
    private static final long DEFAULT_ROLL_TIME = 5000;
    OnPageChangeListener mOuterPageChangeListener;
    private LoopPagerAdapterWrapper mAdapter;
    private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
    private ArrayList<CheckBox> dots;
    private Context ctx;
    private ViewGroup dotParent;
    private int lastPoint = 0;

    private boolean autoable = true;

    /**
     * 在使用 fragmentpageradapter 的时侯可能会用到此方法定位到实际的位置
     *
     * @param position
     * @param count
     * @return (position-1)%count
     */
    public static int toRealPosition(int position, int count) {
        position = position - 1;
        if (position < 0) {
            position += count;
        } else {
            position = position % count;
        }
        return position;
    }

    /**
     * 设置边界缓存,如果设置为true后 ,边界的view(第一个和最后一个)将不会被摧毁,可以防止滑倒边界后视图闪烁
     *
     * @param flag
     */
    public void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
        if (mAdapter != null) {
            mAdapter.setBoundaryCaching(flag);
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mAdapter = new LoopPagerAdapterWrapper(getContext(), (BasicPageAdapter) adapter);
        mAdapter.setBoundaryCaching(mBoundaryCaching);
        super.setAdapter(mAdapter);
        setCurrentItem(0, false);
        if (autoable) {
            startImageTimerTask();
        }
    }

    public BasicPageAdapter<?> getWrappedAdapter() {
        return mAdapter;
    }

    @Override
    public PagerAdapter getAdapter() {
        return mAdapter != null ? mAdapter.getRealAdapter() : null;
    }

    @Override
    public int getCurrentItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mAdapter.toInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }

    public void setDotParent(ViewGroup layout) {
        this.dotParent = layout;
        layout.removeAllViews();
        addDot();
    }

    private void addDot() {
        if (dotParent != null) {
            dotParent.removeAllViews();
            int len = mAdapter.getCount() - 2;
            dots = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                View view = LayoutInflater.from(ctx).inflate(R.layout.view_loop_pager_dot, null);
                CheckBox dot = (CheckBox) view.findViewById(R.id.loop_view_pager_dot_ck);
                dot.setClickable(false);
                dotParent.addView(view);
                dots.add(dot);
            }
            if (dots.size() > 0) {
                dots.get(0).setChecked(true);
                lastPoint = 0;
            }
        }
    }

    public LoopViewPager(Context context) {
        super(context);
        this.ctx = context;
        init();
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init();
    }

    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            if (getAdapter().getCount() > 0) {
                int realPosition = mAdapter.toRealPosition(position);
                if (mPreviousPosition != realPosition) {
                    mPreviousPosition = realPosition;
                    if (mOuterPageChangeListener != null) {
                        mOuterPageChangeListener.onPageSelected(realPosition);
                    }
                }
                if (dots != null) {
                    dots.get(lastPoint).setChecked(false);
                    dots.get(realPosition).setChecked(true);
                    lastPoint = realPosition;
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = position;
            if (mAdapter != null) {
                realPosition = mAdapter.toRealPosition(position);

                if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0 || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            mPreviousOffset = positionOffset;
            if (mOuterPageChangeListener != null) {
                if (mAdapter != null && realPosition != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition, 0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mAdapter != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition = mAdapter.toRealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0 || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    /**
     * 触摸停止计时器，抬起启动计时器
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 开始图片滚动
            if (getAdapter().getCount() > 0 && autoable) {
                startImageTimerTask();
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 停止图片滚动
            stopImageTimerTask();
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        // 图片滚动
        if (mAdapter != null) {
            if (mAdapter.getCount() - 2 > 1 && isStop) {
                mHandler.postDelayed(mImageTimerTask, DEFAULT_ROLL_TIME);
                isStop = false;
            }
        }
    }

    private boolean isStop = true;

    /**
     * 停止头片滚动的任务
     */
    private void stopImageTimerTask() {
        if (mAdapter != null) {
            if (mAdapter.getCount() - 2 > 1 && !isStop) {
                mHandler.removeCallbacks(mImageTimerTask);
                isStop = true;
            }
        }
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (mAdapter != null) {
                int currentItem = getCurrentItem();
                setCurrentItem(currentItem + 1);
                if (!isStop) { // if isStop=true //当你退出后 要把这个给停下来 不然 这个一直存在
                    // 就一直在后台循环
                    mHandler.postDelayed(mImageTimerTask, DEFAULT_ROLL_TIME);
                }

            }
        }
    };

    public void setAutoable(boolean b) {
        autoable = b;
    }

    public void startBanner() {
        startImageTimerTask();
    }

    public void stopBanner() {
        stopImageTimerTask();
    }

}
