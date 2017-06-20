package com.android_mobile.pay.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android_mobile.pay.PayConstants;
import com.android_mobile.pay.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by mxh on 2017/6/13.
 * Describe：微信的支付回调页面
 * 做了部分修改，通过该类同时可以对支付宝的相关回调做出处理
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI wxapi;
    private boolean isAliPay;
    private String appId = PayConstants.WX_APP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_entry);
        if (isWxPayCallBack(getIntent())) {
            isAliPay = false;
            wxapi = WXAPIFactory.createWXAPI(this, appId);
            wxapi.handleIntent(getIntent(), this);
        } else {
            aliHandlerIntent(getIntent());
        }
    }

    /**
     * 是否是微信回调
     */
    private boolean isWxPayCallBack(Intent intent) {
        int errCode = intent.getIntExtra(PayConstants.PAY_ERROR_CODE, 1);
        return errCode == 1;
    }

    /**
     * 处理支付宝的intent
     */
    private void aliHandlerIntent(Intent intent) {
        int errCode = intent.getIntExtra(PayConstants.PAY_ERROR_CODE, -1);
        BaseResp resp = new BaseResp() {

            @Override
            public boolean checkArgs() {
                return false;
            }

            @Override
            public int getType() {
                return ConstantsAPI.COMMAND_PAY_BY_WX;
            }

        };
        resp.errCode = errCode;
        isAliPay = true;
        onResp(resp);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (isWxPayCallBack(intent)) {
            wxapi.handleIntent(intent, this);
        } else {
            aliHandlerIntent(intent);
        }
    }

    @Override
    public void onReq(BaseReq arg0) {

    }

    /**
     * 支付回调 <br/>
     * 0 成功 展示成功页面<br/>
     * -1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。<br/>
     * -2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。<br/>
     */
    @Override
    public void onResp(BaseResp resp) {
        Log.d("error_code", resp.errCode + "   " + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            boolean payResult = false;
            switch (resp.errCode) {
                case 0:
                    payResult = true;
                    break;
                case -1:
                case -2:
                case -3:
                    payResult = false;
                    break;
            }
            notifiService(payResult);
            Intent intent = new Intent();
            // 支付结果页面
            //intent.setClass(WXPayEntryActivity.this, OrderPayResultActivity.class);
            intent.putExtra("pay_result", payResult);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 支付成功通知服务器
     */
    private void notifiService(final boolean isSuccess) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
