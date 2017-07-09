package com.webber.mcorelibspace.demo.net.api;

import com.android_mobile.net.response.BaseResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by mxh on 2017/6/23.
 * Describe：
 */

public interface FileApi {

    @Multipart
    @POST("upload/uploadImageServlet")
    Observable<Response<BaseResponse>> uploadImage(@Part MultipartBody.Part id, @Part MultipartBody.Part body);

    @Multipart
    @POST("upload/uploadImageServlet")
    Observable<Response<BaseResponse>> uploadImg(@Part MultipartBody.Part id, @PartMap() Map<String, RequestBody> bodyMap);

    @GET("/")
    Observable<Response<BaseResponse>> getHttpsPage();
}
