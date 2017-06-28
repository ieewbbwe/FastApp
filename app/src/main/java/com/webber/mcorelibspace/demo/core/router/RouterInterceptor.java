package com.webber.mcorelibspace.demo.core.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.android_mobile.core.utiles.Lg;

/**
 * Created by mxh on 2017/6/22.
 * Describe：拦截Router的请求
 */
@Interceptor(priority = 7)
public class RouterInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Lg.print("webber", "拦截到" + postcard.getPath());
        callback.onContinue(postcard);
       /* //打断传递
        callback.onInterrupt(null);
        //添加信息后传递
        postcard.withString("extra", "我是在拦截器中附加的参数");
        callback.onContinue(postcard);*/
    }

    @Override
    public void init(Context context) {
        Lg.print("webber", "拦截器初始化");
    }
}
