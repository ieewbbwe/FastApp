package com.android_mobile.net;


import com.android_mobile.net.api.AppApi;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mxh on 2016/11/18.
 * Describe：
 */

public class BaseFactory {

    private static AppApi appApiList;

    protected static <T> T createApi(String baseUrl, Converter.Factory factory, Class<T> t) {
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return mBuilder.client(OkHttpFactory.getOkHttpClient()).build().create(t);
    }

    public static AppApi getAppApi() {
        if (appApiList == null) {
            BaseFactory.appApiList = createApi(UrlMgr.Service, GsonConverterFactory.create(), AppApi.class);
        }
        return appApiList;
    }

}
