package com.android_mobile.core.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android_mobile.core.utiles.Lg;

/**
 * Created by picher on 2017/12/25.
 * Describe：自定義WevView
 */

public class CustomerWebView extends WebView implements DefaultWebClientCallBack {
    private static final java.lang.String TAG = "CustomerWebView";
    private DefaultWebClient mDefaultWebClient;
    private boolean isAllowClick = false;

    public CustomerWebView(Context context) {
        this(context,null);
    }

    public CustomerWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (isAllowClick) {
            addImageClickListner();
        }
    }

    // js通信接口
    public class JavascriptInterfaces {
        private Context context;

        JavascriptInterfaces(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(String img) {
            Lg.d(TAG,"ClickImage："+img);
        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        this.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
        this.loadUrl("javascript:(document.getElementsByTagName('body')[0].style.webkitTextSizeAdjust= '250%')()");
    }

    private void init() {
        //配置初始化
        WebSettings webSetting = getSettings();

        webSetting.setSupportZoom(true);
        webSetting.setUseWideViewPort(false);
        webSetting.setJavaScriptEnabled(true);

        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);
        //webSetting.setTextSize(WebSettings.TextSize.LARGEST);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowFileAccess(true);
        //webSetting.setPluginsEnabled(true);
        //webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        setWebChromeClient(new WebChromeClient());


        //添加點擊事件
        addJavascriptInterface(new JavascriptInterfaces(getContext()), "imagelistner");

        mDefaultWebClient = new DefaultWebClient(getContext(), this);
        setWebViewClient(mDefaultWebClient);
    }


    public static class DefaultWebClient extends WebViewClient {

        private final Context context;
        private final DefaultWebClientCallBack callback;

        public DefaultWebClient(Context context, DefaultWebClientCallBack callback) {
            this.context = context;
            this.callback = callback;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(context, description, Toast.LENGTH_LONG).show();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 點擊打開頁面
            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                // handle in app webview deeplink
                context.startActivity(intent);
            } else {

            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            if (callback != null) {
                callback.onPageFinished(view, url);
            }
        }
    }
}
