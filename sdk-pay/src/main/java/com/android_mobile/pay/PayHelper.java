package com.android_mobile.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android_mobile.pay.net.PayApiFactory;
import com.android_mobile.pay.net.RewardAliResp;
import com.android_mobile.pay.net.RewardWxResp;
import com.android_mobile.pay.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mxh on 2017/6/13.
 * Describe：支付帮助类
 */

public class PayHelper implements IPayCore {

    private static final int SDK_PAY_FLAG = 1;

    private Activity act;

    private Handler mHandler = new Handler(act.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Intent intent = new Intent(act, WXPayEntryActivity.class);
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(act, "支付成功", Toast.LENGTH_SHORT).show();
                        intent.putExtra(PayConstants.PAY_ERROR_CODE, 0);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(act, "支付结果确认中", Toast.LENGTH_SHORT)
                                    .show();
                            intent.putExtra(PayConstants.PAY_ERROR_CODE, -3);
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(act, "支付失败", Toast.LENGTH_SHORT)
                                    .show();
                            intent.putExtra(PayConstants.PAY_ERROR_CODE, -2);
                        }
                    }
                    act.startActivity(intent);
                }
                break;
                default:
                    break;
            }
        }
    };

    public PayHelper(Activity activity) {
        this.act = activity;
    }

    /**
     * 获取支付宝订单信息
     *
     * @param orderNumber 订单号
     * @param incomeAmt   支付金额
     */
    @Override
    public Observable<String> createOrderInfo(String orderNumber, double incomeAmt) {
        return PayApiFactory.getPayApi().createAliOrderInfo(orderNumber, incomeAmt)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Response<RewardAliResp>>() {
                    @Override
                    public boolean test(Response<RewardAliResp> rewardPayRespResponse) throws Exception {
                        if (!rewardPayRespResponse.body().isSuccess()) {
                            Toast.makeText(act, rewardPayRespResponse.body().getMessage(), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        return rewardPayRespResponse.isSuccessful()
                                && rewardPayRespResponse.body().isSuccess();
                    }
                }).map(new Function<Response<RewardAliResp>, String>() {
                    @Override
                    public String apply(Response<RewardAliResp> rewardPayRespResponse) throws Exception {
                        return rewardPayRespResponse.body().orderInfo;
                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param orderNum  订单号
     * @param incomeAmt 支付金额
     */
    @Override
    public void aliPrePay(final String orderNum, double incomeAmt) {
        createOrderInfo(orderNum, incomeAmt)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(act);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(s, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                });

        /*PayApiFactory.getPayApi().createAliOrderInfo("00012")
                .subscribeOn(Schedulers.io())
                .filter(new Func1<Response<RewardAliResp>, Boolean>() {
                    @Override
                    public Boolean call(Response<RewardAliResp> rewardPayRespResponse) {
                        return rewardPayRespResponse.isSuccessful()
                                && rewardPayRespResponse.body().isSuccess();
                    }
                })
                .map(new Func1<Response<RewardAliResp>, String>() {
                    @Override
                    public String call(Response<RewardAliResp> rewardPayRespResponse) {
                        return rewardPayRespResponse.body().orderInfo;
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(act);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(s, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });*/
    }

    /**
     * 微信支付
     */
    @Override
    public void wxPrePay(String orderNumber, double incomeAmt) {
        PayApiFactory.getPayApi().createWxOrderInfo(orderNumber, incomeAmt)
                //调度到io线程
                .subscribeOn(Schedulers.io())
                //接收到响应信息 过滤掉响应失败的情况
                .filter(new Predicate<Response<RewardWxResp>>() {
                    @Override
                    public boolean test(Response<RewardWxResp> rewardWxRespResponse) throws Exception {
                        if (!rewardWxRespResponse.body().isSuccess()) {
                            Toast.makeText(act, rewardWxRespResponse.body().getMessage(), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        return rewardWxRespResponse.isSuccessful()
                                && rewardWxRespResponse.body().isSuccess();
                    }
                })
                //转换Response<RewardWxResp> 为RewardWxResp 方便调用
                .map(new Function<Response<RewardWxResp>, RewardWxResp>() {
                    @Override
                    public RewardWxResp apply(Response<RewardWxResp> rewardWxRespResponse) throws Exception {
                        return rewardWxRespResponse.body();
                    }
                }).map(new Function<RewardWxResp, PayReq>() {
            @Override
            public PayReq apply(RewardWxResp rewardWxResp) throws Exception {
                PayReq req = new PayReq();
                req.appId = rewardWxResp.getAppid();
                req.partnerId = rewardWxResp.getPartnerid();
                req.prepayId = rewardWxResp.getPrepayid();
                req.packageValue = "Sign=WXPay";
                req.nonceStr = rewardWxResp.getNoncestr();
                req.timeStamp = rewardWxResp.getTimestamp();
                return req;
            }
        }).subscribe(new Consumer<PayReq>() {
            @Override
            public void accept(PayReq payReq) throws Exception {
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(act, null);
                msgApi.registerApp(PayConstants.WX_APP_ID);
                if (!msgApi.isWXAppInstalled()) {
                    Toast.makeText(act, "未检测到微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                msgApi.registerApp(PayConstants.WX_APP_ID);
                msgApi.sendReq(payReq);
            }
        });
    }
}
