package com.android_mobile.core.ui.banner;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android_mobile.core.R;

import java.util.List;

/**
 * Created by mxh on 2017/6/23.
 * Describe：自定义轮播图控件
 */

public class BannerView extends FrameLayout {

    private LoopViewPager mBannerLvp;
    private LinearLayout mDotLl;
    private LoopPagerAdapter mAdapter;

    public BannerView(@NonNull Context context) {
        this(context, null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View bannerView = LayoutInflater.from(context).inflate(R.layout.view_banner, this, false);
        mBannerLvp = (LoopViewPager) bannerView.findViewById(R.id.m_act_home_banner_lvp);
        mDotLl = (LinearLayout) bannerView.findViewById(R.id.m_act_banner_point_ll);
        addView(bannerView);
    }

    public void setBannerData(List<IBanner> banners) {
        mAdapter = new LoopPagerAdapter(getContext(), banners);
        mBannerLvp.setAdapter(mAdapter);
        mBannerLvp.setDotParent(mDotLl);
    }

    public void setOnImageClickListener(LoopPagerAdapter.OnImageClickListener clickListener) {
        mAdapter.setOnImageClickListener(clickListener);
    }

}
