package com.android_mobile.pay.net;

/**
 * Created by mxh on 2017/6/13.
 * Describe：支付相关接口地址
 */

public interface PayUrlMgr {

    String Service = "";
    /**
     * 创建支付宝订单信息
     */
    String URL_CREATE_ALI_ORDER_INFO = "";

    /**
     * 创建微信订单信息
     */
    String URL_CREATE_WX_ORDER_INFO = "";
}
