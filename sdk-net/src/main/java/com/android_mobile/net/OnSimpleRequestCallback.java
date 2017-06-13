package com.android_mobile.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Response;


/**
 * Created by mxh on 2016/11/22.
 * Describe：不需要展示进度框的网络请求, 但是出现错误会提示
 * 如果你想使用其他方式展示错误信息, 回调里重写方法即可
 */

public abstract class OnSimpleRequestCallback<T extends Response> extends OnResultCallBack<T> {

    public OnSimpleRequestCallback(Context context) {
        super(context);
    }

    @Override
    public void onFailed(int code, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        Log.d("network", String.format("onFailed respCode：%s->> respMessage：%s", code, message));
    }

    @Override
    public void onException(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        Log.d("network", "onException " + message);
    }

    @Override
    public void onFinish() {

    }
}
