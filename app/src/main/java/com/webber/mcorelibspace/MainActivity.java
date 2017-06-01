package com.webber.mcorelibspace;

import android.os.Bundle;
import android.view.View;

import com.android_mobile.core.base.SelectDataActivity;
import com.android_mobile.core.listener.IMediaImageListener;
import com.android_mobile.core.listener.IMediaPicturesListener;

public class MainActivity extends SelectDataActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationBar.hidden();
        findViewById(R.id.m_bottom_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomChoseView();
            }
        });
    }

    @Override
    protected void initComp() {

    }


    @Override
    protected void initListener() {
        setMediaImageListener(new IMediaImageListener() {
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
        });
    }

    @Override
    protected void initData() {

    }
}
