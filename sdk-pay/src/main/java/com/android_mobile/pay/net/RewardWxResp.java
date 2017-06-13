package com.android_mobile.pay.net;

import com.android_mobile.net.response.BaseResponse;


/**
 * Created by mxh on 2017/6/13.
 * Describe:微信支付信息
 */
public class RewardWxResp extends BaseResponse {
    //应用id 微信开放平台审核通过的应用APPID
    private String appid = "";
    //商户号 微信支付分配的商户号
    private String partnerid = "";
    //预支付交易会话ID 预支付交易会话ID
    private String prepayid = "";
    //扩展字段 暂填写固定值Sign=WXPay
    private String noncestr = "";
    //时间戳 时间戳，请见接口规则-参数规定
    private String timestamp = "";
    //签名 签名，详见签名生成算法
    private String sign = "";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
