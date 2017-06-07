package com.android_mobile.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.android_mobile.core.app.UncaughtException;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.manager.SharedPrefManager;

import java.util.Stack;

/**
 * Created by Administrator on 2017/5/30.
 * Describe:
 */

public class BasicApplication extends Application {

    public static Stack<Activity> activityStack = new Stack<Activity>();

    private Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        SharedPrefManager.init(applicationContext);
        ImageLoadFactory.init(applicationContext);
        Thread.setDefaultUncaughtExceptionHandler(UncaughtException.getInstance(applicationContext));
    }


}
