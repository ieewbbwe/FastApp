package cn.sharesdk;

import android.content.Context;
import android.util.Log;
import android.view.View;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static cn.sharesdk.ShareDialog.EMAIL;
import static cn.sharesdk.ShareDialog.SINA;
import static cn.sharesdk.ShareDialog.TENCENT;
import static cn.sharesdk.ShareDialog.WE_CHART;
import static cn.sharesdk.ShareDialog.WE_CIRCLE;

/**
 * Created by mxh on 2017/1/22.
 * Describe：分享功能封装类
 */

public class SocialShareHelper {

    public static final String SOCIAL_LOGIN = "com.umeng.login";
    public static final String SOCIAL_SHARE = "com.umeng.share";
    private final Context mContext;
    private ShareDialog mShareDialog;

    private String url = "http://baidu.com";
    private String title = "推荐给您一款好用的APP，快来试试吧！";
    private String titleUrl = "应用下载地址";
    private String imageUrl = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496903774&di=f35e35047e31bec59205845f28a73ca7&src=http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg";
    private String content = "应用描述信息";

    public SocialShareHelper(Context context, String s) {
        this.mContext = context;
        initPlatform(context);
    }

    /**
     * 初始化平台
     */
    private void initPlatform(Context context) {
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
        if (media.equals(WE_CHART)) {
            //showShare(mContext, null, true);
            showShare(mContext, Wechat.NAME, true);

        } else if (media.equals(WE_CIRCLE)) {
            showShare(mContext, WechatMoments.NAME, true);

        } else if (media.equals(TENCENT)) {
            showShare(mContext, QQ.NAME, true);

        } else if (media.equals(EMAIL)) {
            showShare(mContext, Email.NAME, true);

        } else if (media.equals(SINA)) {
            showShare(mContext, SinaWeibo.NAME, true);
        }
    }

    /**
     * 演示调用ShareSDK执行分享
     */
    public void showShare(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            Log.d("webber", "分享名：" + platformToShare);
            oks.setPlatform(platformToShare);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imageUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public class Builder {
        private Context mContext;
        private SocialShareHelper socialComponent;

        private String url;
        private String title;
        private String titleUrl;
        private String imageUrl;
        private String content;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setTitleUrl(String titleUrl) {
            this.titleUrl = titleUrl;
            return this;
        }

        public Builder setImageUrl(String imgUrl) {
            this.imageUrl = imgUrl;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public void Builder() {
            socialComponent = new SocialShareHelper(mContext, SocialShareHelper.SOCIAL_SHARE);
            socialComponent.setUrl(url);
            socialComponent.setTitle(title);
            socialComponent.setTitleUrl(titleUrl);
            socialComponent.setContent(content);
            socialComponent.setImageUrl(imageUrl);
        }
    }
}
