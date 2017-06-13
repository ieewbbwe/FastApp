package com.android_mobile.net;

/**
 * Created by mxh on 2016/11/18.
 * Describe：网络请求常量类
 */
public interface ApiConstants {

    /**
     * 无网络连接
     */
    int ERROR_NO_INTERNET = -1;
    /**
     * 客户端错误
     */
    int ERROR_CLIENT_AUTHORIZED = 1;
    /**
     * 用户授权失败
     */
    int ERROR_USER_AUTHORIZED = 2;
    /**
     * 请求参数错误
     */
    int ERROR_REQUEST_PARAM = 3;
    /**
     * 参数检验不通过
     */
    int ERROR_PARAM_CHECK = 4;
    /**
     * 自定义错误
     */
    int ERROR_OTHER = 10;

    String BASE_URL = "http://api.tianapi.com";

    String KEY_WX = "71ad915116b186bfa0373f7d880be1c5";
}
