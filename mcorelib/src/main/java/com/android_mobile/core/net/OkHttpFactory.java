package com.android_mobile.core.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by mxh on 2016/11/18.
 * Describe：
 */

public class OkHttpFactory {

    private OkHttpClient client;
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;

    private OkHttpFactory() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                //.cache() //缓存
                .addInterceptor(new UserAgentInterceptor())//自定义拦截器
                .addInterceptor(interceptor)//Log日志
                .retryOnConnectionFailure(true) //失败重连
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                //.cookieJar(new CookieManger())//cookie管理
                .build();
    }

    public static OkHttpClient getOkHttpClient() {
        return Holder.INSTANCE.client;
    }

    public static class Holder {
        final public static OkHttpFactory INSTANCE = new OkHttpFactory();
    }
}
