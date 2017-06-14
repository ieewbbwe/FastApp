package com.webber.mcorelibspace.demo.net.request;

import com.android_mobile.net.request.BaseRequest;

/**
 * Created by mxh on 2016/11/24.
 * Describe：热门新闻请求参数封装
 */
public class TopNewsRequest extends BaseRequest {
    private String key;
    private int num;
    private int page;

    public TopNewsRequest(String key, int num, int page) {
        this.key = key;
        this.num = num;
        this.page = page;
    }
}
