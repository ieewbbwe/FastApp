package com.android_mobile.net;

import com.webber.mcorelibspace.demo.net.SslContextFactory;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by mxh on 2016/11/18.
 * Describe：
 */

public class OkHttpFactory {

    private OkHttpClient client;
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;
    private static long DEFAULT_CACHE_SIZE = 1024 * 1024 * 100;//100mb
    private static File mCacheFile;
    private static InputStream[] mCertificates;

    public static void init(File cacheFile, InputStream... inputStreams) {
        mCacheFile = cacheFile;
        mCertificates = inputStreams;
    }

    private OkHttpFactory() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor())//自定义拦截器
                .addInterceptor(interceptor)//Log日志
                //.addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                .retryOnConnectionFailure(true) //失败重连
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        //Https支持
        if (mCertificates != null) {
            SSLSocketFactory sslSocketFactory = new SslContextFactory().getSSLSocketFactory(mCertificates);
            clientBuilder.sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory));
        }
        //缓存
        if (mCacheFile != null) {
            clientBuilder.cache(new Cache(mCacheFile, DEFAULT_CACHE_SIZE));
        }
        client = clientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return Holder.INSTANCE.client;
    }

    private static class Holder {
        final static OkHttpFactory INSTANCE = new OkHttpFactory();
    }

}
