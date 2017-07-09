package com.webber.mcorelibspace.demo.core;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.utiles.IntentUtils;
import com.webber.mcorelibspace.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/router/uriTest")
public class UriTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri_test);
    }

    @OnClick(R.id.m_uri_bt)
    public void openUri() {
        toast("点击了我");
        startActivity(IntentUtils.getImageFileIntent(getThisContext(),"/storage/emulated/0/DCIM/Carmera/IMG_20170601_142846.jpg"));
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
}
