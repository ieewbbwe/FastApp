package com.android_mobile.net;

import android.content.Context;

import com.android_mobile.net.response.BaseResponse;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by mxh on 2016/11/22.
 * Describe：回调结果封装类
 */

public abstract class OnResultCallBack<T extends Response> extends Subscriber<T> {

    /**
     * When response succeed
     *
     * @param response data from api
     */
    public abstract void onResponse(T response);

    /**
     * Weather succeed or error the final method
     */
    public abstract void onFinish();

    public OnResultCallBack() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFinish();
    }

    @Override
    public void onNext(T response) {
        if (response.isSuccessful() && isOk(response)) {
            onResponse(response);
        } else {
            if (response.body() instanceof BaseResponse) {
                onError(new ApiException(String.valueOf(response.code()), ((BaseResponse) response.body()).getMessage()));
            } else {
                onError(new ApiException(String.valueOf(response.code()), "请求失败"));
            }
        }

    }

    /**
     * 自定义响应数据拦截
     *
     * @param response 响应结果
     * @return true pass false not
     */
    private boolean isOk(T response) {
        if (response.body() instanceof BaseResponse) {
            return true;
        } else {
            return true;
        }
    }

}
