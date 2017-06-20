package com.webber.mcorelibspace.demo.net;

import com.webber.mcorelibspace.demo.net.response.TopNewsResponse;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by mxh on 2017/6/14.
 * Describe：
 */

public interface NewsApi {
    @GET("wxnew")
    Observable<Response<TopNewsResponse>> getTopNews(@QueryMap Map<String, String> map);
}
