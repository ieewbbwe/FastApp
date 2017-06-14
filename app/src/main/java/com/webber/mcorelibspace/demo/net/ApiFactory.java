package com.webber.mcorelibspace.demo.net;

import com.android_mobile.net.BaseFactory;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mxh on 2017/6/14.
 * Describe：api接口工厂
 */

public class ApiFactory extends BaseFactory {
    private static NewsApi newsApiList;

    public static NewsApi getNewsApi() {
        if (newsApiList == null) {
            newsApiList = createApi("http://api.tianapi.com", GsonConverterFactory.create(), NewsApi.class);
        }
        return newsApiList;
    }
}
