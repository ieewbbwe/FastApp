package com.android_mobile.pay.net;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mxh on 2017/6/13.
 * Describe：支付相关接口
 */

public interface PayApi {

    @GET(PayUrlMgr.URL_CREATE_ALI_ORDER_INFO)
    Observable<Response<RewardAliResp>> createAliOrderInfo(@Query("orderNumber") String orderNum, @Query("price") long incomeAmt);

    @GET(PayUrlMgr.URL_CREATE_WX_ORDER_INFO)
    Observable<Response<RewardWxResp>> createWxOrderInfo(@Query("orderNumber") String orderNum, @Query("price") long incomeAmt);
}
