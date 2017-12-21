package com.android_mobile.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by picher on 2017/12/17.
 * Describe：加载进度条
 */

public class LoadingDialog extends ProgressDialog{
    public LoadingDialog(Context context) {
        super(context,0);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        init(getContext());
    }
    private void init(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.lib_net_loading_layout);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
}
