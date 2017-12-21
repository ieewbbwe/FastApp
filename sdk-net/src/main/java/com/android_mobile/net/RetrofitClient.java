package com.android_mobile.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;

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
                //自定义Gson转化器
                .addConverterFactory(CustomerGsonFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //创建
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
