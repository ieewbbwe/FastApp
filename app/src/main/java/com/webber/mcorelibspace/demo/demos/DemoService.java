package com.webber.mcorelibspace.demo.demos;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android_mobile.core.utiles.Lg;

public class DemoService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Lg.print("picher", "onBind");
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Lg.print("picher", "onCreate 线程：" + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Lg.print("picher", "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Lg.print("picher", "onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Lg.print("picher", "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Lg.print("picher", "onDestroy");

        super.onDestroy();
    }
}
