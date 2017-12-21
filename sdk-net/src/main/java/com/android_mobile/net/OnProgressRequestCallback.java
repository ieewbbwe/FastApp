package com.android_mobile.net;

import android.content.Context;

import retrofit2.Response;

/**
 * Created by mxh on 2016/11/22.
 * Describe： 需要展示进度框的网络访问
 */

public abstract class OnProgressRequestCallback<T extends Response> extends OnSimpleRequestCallback<T> {

    private final Context mContext;
    private LoadingDialog mLoadingDialog;

    public OnProgressRequestCallback(Context context) {
        this.mContext = context;
    }

    @Override
    public void onFinish() {
        if (mContext != null) {
            hideProgressDialog();
        }
    }

    private void hideProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }
}
