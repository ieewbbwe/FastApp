package com.webber.mcorelibspace.demo.share;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.webber.mcorelibspace.R;

import cn.sharesdk.SocialShareHelper;

public class ShareActivity extends AppCompatActivity {

    private SocialShareHelper shareHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        shareHelper = new SocialShareHelper.Builder(this)
                .setTitle("fastapp")//分享标题
                .setTitleUrl("www.baidu.com")//分享的链接，一般为APP下载地址
                .setContent("给您推荐一款很好用的APP！")//分享内容
                .setImageUrl("")//分享的图片地址
                .create();
        findViewById(R.id.m_share_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareHelper.show();
            }
        });
    }

}
