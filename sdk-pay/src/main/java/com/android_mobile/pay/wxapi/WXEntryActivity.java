
package com.android_mobile.pay.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android_mobile.pay.PayConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    /**
     * 分享到微信接口
     **/
    private IWXAPI mWxApi;
    private String appId = PayConstants.WX_APP_ID;
    
    protected void onActivityCreate(Bundle savedInstanceState) {
        mWxApi = WXAPIFactory.createWXAPI(this, appId, false);
        mWxApi.registerApp(appId);
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWxApi = WXAPIFactory.createWXAPI(this, appId, false);
        mWxApi.registerApp(appId);
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent, this);
    }

    /***
     * 请求微信的相应码
     * @param baseResp 响应信息
     */
    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";//成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "分享取消";//取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";//被拒绝
                break;
            default:
                result = "分享返回";//返回
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    /**
     * 微信主动请求我们
     **/
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    private void goToGetMsg() {
//		 Intent intent = new Intent(this, GetFromWXActivity.class);
//		 intent.putExtras(getIntent());
//		 startActivity(intent);
//		 finish();
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
        Log.d("kentwx", "text");
        StringBuffer msg = new StringBuffer();
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);

        finish();
    }
}