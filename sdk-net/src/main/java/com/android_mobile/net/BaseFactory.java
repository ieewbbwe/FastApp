package com.android_mobile.net;


import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by mxh on 2016/11/18.
 * Describe：
 */

public class BaseFactory {

    protected static <T> T createApi(String baseUrl, Converter.Factory factory, Class<T> t) {
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return mBuilder.client(OkHttpFactory.getOkHttpClient()).build().create(t);
    }

}
