package com.webber.mcorelibspace.demo.net;

import com.android_mobile.net.BaseFactory;
import com.webber.mcorelibspace.demo.net.api.FileApi;
import com.webber.mcorelibspace.demo.net.api.NewsApi;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mxh on 2017/6/14.
 * Describe：api接口工厂
 */

public class ApiFactory extends BaseFactory {
    private static NewsApi newsApiList;
    private static FileApi fileApiList;

    public static <T> T createApi(String baseUrl, Class<T> t) {
        return createApi(baseUrl, GsonConverterFactory.create(), t);
    }

    public static NewsApi getNewsApi() {
        if (newsApiList == null) {
            newsApiList = createApi("http://api.tianapi.com", GsonConverterFactory.create(), NewsApi.class);
        }
        return newsApiList;
    }

    public static FileApi getFileApi() {
        if (fileApiList == null) {
            fileApiList = createApi("127.0.0.1:8080", GsonConverterFactory.create(), FileApi.class);
        }
        return fileApiList;
    }

}
