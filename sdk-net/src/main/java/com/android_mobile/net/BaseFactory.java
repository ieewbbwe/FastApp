package com.android_mobile.net;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by mxh on 2016/11/18.
 * Describe：
 */

public class BaseFactory {

    protected static <T> T createApi(String baseUrl, Converter.Factory factory, Class<T> t) {
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return mBuilder.client(OkHttpFactory.getOkHttpClient()).build().create(t);
    }

}
