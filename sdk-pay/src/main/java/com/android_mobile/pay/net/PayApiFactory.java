package com.android_mobile.pay.net;

import com.android_mobile.net.BaseFactory;
import com.android_mobile.net.UrlMgr;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mxh on 2017/6/13.
 * Describe：
 */

public class PayApiFactory extends BaseFactory {
    private static PayApi payApiList;

    public static PayApi getPayApi() {
        if (payApiList == null) {
            payApiList = createApi(UrlMgr.Service, GsonConverterFactory.create(), PayApi.class);
        }
        return payApiList;
    }
}
