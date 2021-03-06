package com.android_mobile.pay.net;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mxh on 2017/6/13.
 * Describe：支付相关接口
 */

public interface PayApi {

    @GET(PayUrlMgr.URL_CREATE_ALI_ORDER_INFO)
    Observable<Response<RewardAliResp>> createAliOrderInfo(@Query("orderNumber") String orderNum, @Query("price") double incomeAmt);

    @GET(PayUrlMgr.URL_CREATE_WX_ORDER_INFO)
    Observable<Response<RewardWxResp>> createWxOrderInfo(@Query("orderNumber") String orderNum, @Query("price") double incomeAmt);
}
