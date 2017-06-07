package com.webber.mcorelibspace;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android_mobile.core.BasicActivity;
import com.android_mobile.core.ui.CountDownTextView;
import com.android_mobile.core.utiles.IntentUtils;
import com.android_mobile.share.SocialComponent;

public class MainActivity extends BasicActivity {

    private SocialComponent mShareComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mHtmlTv = (TextView) findViewById(R.id.m_link_tv);
        mHtmlTv.setText(Html.fromHtml("<b>哈哈哈</b>这是一段<a href=''>www.baidu.com</a>，点击一下看"));

        CountDownTextView downTextView = (CountDownTextView) findViewById(R.id.m_count_down_tv);
        downTextView.setTime(1000 * 10);
        downTextView.toStart();
        mShareComp = new SocialComponent(this, SocialComponent.SOCIAL_SHARE);

        navigationBar.hidden();
        findViewById(R.id.m_bottom_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showBottomChoseView();
                //component.share(MainActivity.this);
                //mShareComp.show();
                IntentUtils.shareApplication(MainActivity.this, getPackageName(), "www.baidu.com");
            }
        });

    }

    @Override
    protected void initComp() {

    }


    @Override
    protected void initListener() {
     /*   setMediaImageListener(new IMediaImageListener() {
            @Override
            public void mediaImagePath(String imagePath) {
                toast(imagePath);
            }
        });
        setMediaPictureListener(new IMediaPicturesListener() {
            @Override
            public void mediaPicturePath(String imagePath) {
                toast(imagePath);
            }
        });*/
    }

    @Override
    protected void initData() {

    }
}
