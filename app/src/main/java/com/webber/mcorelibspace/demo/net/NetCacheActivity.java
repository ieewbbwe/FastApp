package com.webber.mcorelibspace.demo.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.net.response.InitDataResponse;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Retrofit + RxJava 网络数据缓存示例
 * 1 Okhttp 带有的缓存
 * 2 手动cache缓存
 */
public class NetCacheActivity extends AppCompatActivity {

    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_cache);

        findViewById(R.id.m_request_bt).setOnClickListener(v -> requestData());

        mContentTv = (TextView) findViewById(R.id.m_content_tv);
    }

    private void requestData() {
        ApiFactory.getDemoApi().requestInitData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(initDataResponseResponse -> {
                    mContentTv.setText(new Gson().toJson(initDataResponseResponse));
                });
    }
}
