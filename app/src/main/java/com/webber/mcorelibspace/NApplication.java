package com.webber.mcorelibspace;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android_mobile.core.BasicApplication;
import com.android_mobile.core.app.UncaughtException;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.location.LocationHelper;
import com.android_mobile.net.OkHttpFactory;

import java.io.InputStream;

/**
 * Created by mxh on 2016/11/23.
 * Describe：
 */

public class NApplication extends BasicApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LocationHelper.init(getApplicationContext());
        ImageLoadFactory.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(UncaughtException.getInstance(getApplicationContext()));
        //支持Https需要设置该证书
        OkHttpFactory.init(getExternalCacheDir(), (InputStream[]) null);
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
    }
}
