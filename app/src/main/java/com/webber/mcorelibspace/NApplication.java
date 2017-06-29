package com.webber.mcorelibspace;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.location.LocationHelper;
import com.android_mobile.net.OkHttpFactory;

import java.io.IOException;

/**
 * Created by mxh on 2016/11/23.
 * Describe：
 */

public class NApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocationHelper.init(getApplicationContext());
        ImageLoadFactory.init(getApplicationContext());
        ARouter.init(this);
        //支持Https需要设置该证书
        try {
            OkHttpFactory.init(getCacheDir(), getAssets().open("auction_yql.crt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
