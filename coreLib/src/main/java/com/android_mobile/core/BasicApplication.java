package com.android_mobile.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.android_mobile.core.helper.image.ImageLoadFactory;
import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.net.ThreadPool;
import com.android_mobile.core.utiles.CacheUtil;

import java.util.Stack;

public class BasicApplication extends Application {

    public static Stack<Activity> activityStack = new Stack<Activity>();
    private Context context;
    public static int helpFlag = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CacheUtil.context = context;
        helpFlag = CacheUtil.getInteger("app_help_view");
        ImageLoadFactory.init(context);
        SharedPrefManager.init(context);
    }

    public static void closeApp() {
        ThreadPool.basicPool.shutdown();
        ThreadPool.fixedPool.shutdown();
        while (BasicApplication.activityStack.size() > 0) {
            BasicApplication.activityStack.pop().finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.gc();
        System.exit(0);
    }

}
