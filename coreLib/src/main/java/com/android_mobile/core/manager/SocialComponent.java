package com.android_mobile.core.manager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.widget.Toast;

import com.android_mobile.core.R;
import com.android_mobile.core.utiles.Lg;


/**
 * 分享的二次封装
 *
 * @author mxh
 */
public class SocialComponent {
    public static final String SOCIAL_LOGIN = "com.umeng.login";
    public static final String SOCIAL_SHARE = "com.umeng.share";

    Activity mContext;
    private String mContent;
    private String mLink;
    private String mTitle;

    public SocialComponent(Activity context) {
        this.mContext = context;
        initPlatform(context);
    }

    private void initPlatform(Activity context) {

    }

    /**
     * 开始分享
     */
    public void share(Activity activity) {
        this.mContext = activity;
        this.mContent = "推荐给您";
        this.mLink = "http://www.androidblog.cn/index.php";
        this.mTitle = "标题";
        //this.isShareApp = isShareApp;
        showIntentChose();

        //自定义分享面板逻辑 需要时修改
       /* if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);
            mShareDialog.setOnSharePlatformListener(this);
        }
        mShareDialog.show();*/
        Lg.print("webber", "shareContent:" + mContent + mLink);
    }

    private void showIntentChose() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, mTitle);
        intent.putExtra(Intent.EXTRA_TEXT, mContent + mLink);
        try {
            mContext.startActivity(
                    Intent.createChooser(intent,
                            mContext.getResources().getString(R.string.label_chose_share_comp)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext, mContext.getString(R.string.NotFoundActivityNotify), Toast.LENGTH_SHORT).show();
        }
    }

   /* public void switchShare(View v, String media) {
        switch (media) {
            case Constants.SMS:
                Uri smsUri = Uri.parse("smsto:");
                Intent sendSms = new Intent(Intent.ACTION_VIEW, smsUri);
                sendSms.putExtra("sms_body", mContent + mLink);
                sendSms.setType("vnd.android-dir/mms-sms");

                try {
                    mContext.startActivity(sendSms);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, mContext.getString(R.string.NotFoundActivityNotify_sms), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.EMAIL:
                Intent sendEmail = new Intent(Intent.ACTION_SEND);
                sendEmail.setType("plain/text");
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, mTitle);
                sendEmail.putExtra(Intent.EXTRA_TEXT, mContent + mLink);
                try {
                    mContext.startActivity(
                            Intent.createChooser(sendEmail,
                                    mContext.getResources().getString(R.string.select_mail_to_send)));
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, mContext.getString(R.string.NotFoundActivityNotify_email), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.WHATSAPP:
                Intent sendWhatsapp = new Intent(Intent.ACTION_SEND);
                sendWhatsapp.setType("text/plain");
                sendWhatsapp.setPackage("com.whatsapp");
                sendWhatsapp.putExtra(Intent.EXTRA_TEXT, mContent + mLink);

                try {
                    mContext.startActivity(sendWhatsapp);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, mContext.getString(R.string.NotFoundActivityNotify_whatsapp), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.LINE:
                Intent sendLine = new Intent(Intent.ACTION_SEND);
                sendLine.setType("text/plain");
                sendLine.setPackage("jp.naver.line.android");
                sendLine.putExtra(Intent.EXTRA_TEXT, mContent + mLink);
                try {
                    mContext.startActivity(sendLine);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, mContext.getString(R.string.NotFoundActivityNotify_line), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.FACEBOOK:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, isShareApp ? "https://play.google.com/store/apps/details?id=com.yahoo.hkdeals&hl=zh_HK" : mContent + mLink);

                // See if official Facebook app is found
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = mContext.getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }
                // As fallback, launch sharer.php in a browser
                if (!facebookAppFound) {
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" +
                            (isShareApp ? "https://play.google.com/store/apps/details?id=com.yahoo.hkdeals&hl=zh_HK" : mLink);
                    Lg.print("webber", "shareUrl:" + sharerUrl);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }
                mContext.startActivity(intent);
                break;
            case Constants.WEIXIN:
                Lg.print("webber", "微信分享");
                showWXShareWindow(mContext, ((NActivity) mContext).getWindow().getDecorView(), mTitle, mContent, mLink, isShareApp);
                break;
        }
    }

    *//**
     * wx share
     *//*
    private static IWXAPI api;
    private static PopupWindow mPopupWindow = null;
    private static LinearLayout mLayout;

    private static void showWXShareWindow(final Context context, final View view, final String title, final String content, final String link, final boolean isShareApp) {
        if (mPopupWindow == null) {
            mLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.wx_item_popupwindow_layout, null);
            mPopupWindow = new PopupWindow(mLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
            mPopupWindow.setAnimationStyle(R.style.dialog_animator);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        }
        mLayout.findViewById(R.id.wx_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxSendMessage(context, SendMessageToWX.Req.WXSceneSession, title, content, link, isShareApp);
                mPopupWindow.dismiss();
            }
        });
        mLayout.findViewById(R.id.wx_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxSendMessage(context, SendMessageToWX.Req.WXSceneTimeline, title, content, link, isShareApp);
                mPopupWindow.dismiss();
            }
        });
        Lg.print("webber", "顯示微信分享");
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        mPopupWindow.update();
    }

    private static void regToWx(Context context) {
        api = WXAPIFactory.createWXAPI(context, ConfigConstants.WX_APP_ID, true);
        api.registerApp(ConfigConstants.WX_APP_ID);
    }

    private static void wxSendMessage(Context context, int scene, String title, String content, String link, boolean isShareApp) {
        regToWx(context);

        if (!isShareApp) {
            // System.out.println("==9900========"+link + "?&");
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = link; // 点击跳转的地址。
            // webpage.webpageUrl = link + "?&"; // 点击跳转的地址。
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title; // 链接标题
            msg.description = content; // 链接内容

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "webpage" + String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = scene;
            api.sendReq(req);
        } else {
            // 初始化一个WXTextObject对象
            WXTextObject textObj = new WXTextObject();
            textObj.text = content + link;
            // 用WXTextObject对象初始化一个WXMediaMessage对象
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = textObj;
            // 发送文本类型的消息时，title字段不起作用
            // msg.title = "Will be ignored";
            msg.description = content + link;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "webpage" + String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = scene;
            api.sendReq(req);
        }
    }*/

}
