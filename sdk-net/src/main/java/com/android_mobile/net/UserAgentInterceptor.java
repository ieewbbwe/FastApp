package com.android_mobile.net;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mxh on 2016/11/18.
 * Describe：自定义拦截器
 */

public class UserAgentInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request userRequest = originalRequest.newBuilder()
                //添加统一的头信息
                //.addHeader("Content-Type","application/json;charset=UTF-8")
                //.addHeader("APP_TOKEN","MjEyZDUzZWQ2ZTRiNDZmNmFjNTFhNWU4OGQ3NjRkODE7MTIz")
                .build();

        Log.d("network", "request:" + new Gson().toJson(userRequest));
        return chain.proceed(userRequest);
    }
}
