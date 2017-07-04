package com.webber.mcorelibspace.demo.core.banner;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.ui.banner.BannerView;
import com.android_mobile.core.ui.banner.IBanner;
import com.webber.mcorelibspace.R;

import java.util.ArrayList;
import java.util.List;


@Route(path = "/router/banner")
public class BannerActivity extends BaseActivity {

    private BannerView mBannerBv;
    private List<IBanner> iBanners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mNavigationBar.setTitle("轮播图");
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        iBanners = new ArrayList<>();
        mBannerBv = (BannerView) findViewById(R.id.m_banner_bv);
        iBanners.add(new BannerBean("https://s.yimg.com/xr/a0e93b61bbfc53ad22635481af5e44294c156951.jpg"));
        iBanners.add(new BannerBean("https://s.yimg.com/xr/8c0d52a5072a016dafcf8db0f56b990cc9659cb9.jpg"));
        iBanners.add(new BannerBean("https://s.yimg.com/xr/aa5c528bb2d91dbbbd7935a83e8aa982f8895468.jpg"));
        iBanners.add(new BannerBean("https://s.yimg.com/xr/5b1329a981195dd9ed8af022cfef9a339a9bcdab.jpg"));
        mBannerBv.setBannerData(iBanners);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        mBannerBv.setOnImageClickListener(pos -> {
            toast("点击了第" + pos + "个图片");
            Intent intent = new Intent(BannerActivity.this, ShowPhotoActivity.class);
            intent.putExtra("imgpath", iBanners.get(pos).getImageUrl());
            startActivity(intent);
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
}
