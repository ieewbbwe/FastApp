package com.webber.mcorelibspace.demo.core.banner;

import com.android_mobile.core.ui.banner.IBanner;

/**
 * Created by mxh on 2017/6/23.
 * Describe：
 */

public class BannerBean implements IBanner {
    private String imgPath;


    public BannerBean(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String getImageUrl() {
        return imgPath;
    }
}
