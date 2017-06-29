package com.webber.mcorelibspace.demo.net.api;

import com.android_mobile.net.response.BaseResponse;
import com.webber.mcorelibspace.demo.net.response.QueryProductResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mxh on 2017/6/29.
 * Describe：
 */

public interface YahooApi {
    @GET("v1/public/yql/")
    Observable<Response<QueryProductResponse>> getSearchProduct(@Query("q") String query, @Query("isClear") boolean isClear, @Query("format") String format);

    @GET("/")
    Observable<Response<BaseResponse>> get12306Page();
}
