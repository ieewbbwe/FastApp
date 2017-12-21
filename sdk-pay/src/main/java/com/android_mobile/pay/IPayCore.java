package com.android_mobile.pay;


import io.reactivex.Observable;

/**
 * Created by mxh on 2017/6/13.
 * Describe：支付类核心方法
 */

public interface IPayCore {

    /**
     * 获取订单信息
     *
     * @param orderNumber 订单号
     * @param incomeAmt   支付金额
     */
    Observable<String> createOrderInfo(String orderNumber, double incomeAmt);

    /**
     * 支付宝支付
     *
     * @param orderNumber 订单号
     * @param incomeAmt   支付金额
     */
    //TODO 制作成参数集合，写死不好
    void aliPrePay(String orderNumber, double incomeAmt);

    /**
     * 微信支付
     *
     * @param orderNumber 订单号
     * @param incomeAmt   支付金额
     */
    void wxPrePay(String orderNumber, double incomeAmt);


}
