package com.android_mobile.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mxh on 2016/11/18.
 * Describe：
 */

public enum RetrofitClient {
    INSTANCE;

    private final Retrofit retrofit;

    RetrofitClient() {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OkHttpFactory.getOkHttpClient())
                //baseUrl
                .baseUrl(ApiConstants.BASE_URL)
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //创建
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
