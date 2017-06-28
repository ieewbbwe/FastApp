package com.android_mobile.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android_mobile.net.response.BaseResponse;
import com.google.gson.Gson;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by mxh on 2016/11/22.
 * Describe：回调结果封装类
 */

public abstract class OnResultCallBack<T extends Response> extends Subscriber<T> {

    protected final Context mContext;

    public abstract void onFailed(int code, String message);

    public abstract void onException(String message);

    public abstract void onResponse(T response);

    public abstract void onFinish();

    public OnResultCallBack(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isNetworkAvailable(mContext)) {
            onFailed(ApiConstants.ERROR_NO_INTERNET, mContext.getString(R.string.label_network_unavailable));
            onFinish();
            unsubscribe();
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onException("请求失败");
        onFinish();
    }

    @Override
    public void onNext(T response) {
        if (response.isSuccessful() && isOk(response)) {
            Log.d("network", "response:" + new Gson().toJson(response));
            onResponse(response);
        } else {
            if (response.body() instanceof BaseResponse) {
                onFailed(response.code(), ((BaseResponse) response.body()).getMessage());//response.message());
            } else {
                onFailed(response.code(), "请求失败");//response.message());
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

    /**
     * 检测网络连接是否可用
     *
     * @return true 可用; false 不可用
     */
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        if (netInfo == null) {
            return false;
        }
        for (NetworkInfo aNetInfo : netInfo) {
            if (aNetInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

}
