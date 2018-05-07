package com.webber.mcorelibspace.demo.net.api;

import com.webber.mcorelibspace.demo.net.response.InitDataResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by picher on 2018/5/7.
 * Describe：
 */

public interface DemoApi {

    @GET("api/v1/initData")
    Observable<Response<InitDataResponse>> requestInitData();
}
