package com.webber.mcorelibspace;

import android.app.Application;

import com.android_mobile.location.LocationHelper;

/**
 * Created by mxh on 2016/11/23.
 * Describe：
 */

public class NApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocationHelper.init(getApplicationContext());

    }
}
