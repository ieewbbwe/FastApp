package com.android_mobile.share;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.android_mobile.share.onekeyshare.OnekeyShare;
import com.android_mobile.share.onekeyshare.OnekeyShareTheme;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static com.android_mobile.share.ShareDialog.BROWSER;
import static com.android_mobile.share.ShareDialog.COPY_LINK;
import static com.android_mobile.share.ShareDialog.TENCENT;
import static com.android_mobile.share.ShareDialog.WE_CHART;
import static com.android_mobile.share.ShareDialog.WE_CIRCLE;

/**
 * Created by mxh on 2017/1/22.
 * Describe：分享功能封装类
 */

public class SocialComponent {

    public static final String SOCIAL_LOGIN = "com.umeng.login";
    public static final String SOCIAL_SHARE = "com.umeng.share";
    private final Activity mContext;
    private ShareDialog mShareDialog;

    private String url = "http://baidu.com";
    private String title = "我的应用";
    private String titleUrl = "http://baidu.com";
    private String imageUrl = "http://img.blog.csdn.net/20170318123805398?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbW94aW91aGFv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast";
    private String content = "向您推荐一款APP，快来试试吧！";

    public SocialComponent(Activity context, String s) {
        this.mContext = context;
        initPlatform(context);
    }

    /**
     * 初始化平台
     */
    private void initPlatform(Activity context) {
        ShareSDK.initSDK(context);
    }

    public void show() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);
            mShareDialog.setOnSharePlatformListener(mShareClickListener);
        }
        mShareDialog.show();
    }

    /**
     * 分享的对调
     */
    private ShareDialog.OnSharePlatformListener mShareClickListener = new ShareDialog.OnSharePlatformListener() {
        @Override
        public void onShare(View v, ShareDialog dialog, SharePlatform platform, String media, int index) {
            switchShare(v, media);
            dialog.dismiss();
        }
    };

    private void switchShare(View v, String media) {
        switch (media) {
            case WE_CHART:
                //showShare(mContext, null, true);
                showShare(mContext, Wechat.NAME, true);
                break;
            case WE_CIRCLE:
                showShare(mContext, WechatMoments.NAME, true);
                break;
            case TENCENT:
                showShare(mContext, QQ.NAME, true);
                break;
            case BROWSER:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                mContext.startActivity(intent);
                break;
            case COPY_LINK:
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setPrimaryClip(ClipData.newPlainText(null, "http://sharesdk.cn"));
                Toast.makeText(mContext, "已复制到剪贴板", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 演示调用ShareSDK执行分享
     */
    public void showShare(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle(title);
        oks.setTitleUrl(titleUrl);
        oks.setText(content);
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
        oks.setImageUrl(imageUrl);
        oks.setUrl("http://www.mob.com"); //微信不绕过审核分享链接
        //oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
        oks.setComment("分享"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite(title);  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://mob.com");//QZone分享参数
        oks.setVenueName(title);
        oks.setVenueDescription("This is a beautiful place!");
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        String label = title;
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

            }
        };
        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }
}
