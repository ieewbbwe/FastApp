package com.webber.mcorelibspace.demo.core.banner;

import android.os.Bundle;
import android.view.View;

import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.ui.photoview.PhotoView;
import com.webber.mcorelibspace.R;

public class ShowPhotoActivity extends BaseActivity {

    private PhotoView mPhotoV;
    private View mBackIv;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        mNavigationBar.hidden();
    }

    @Override
    protected void initComp() {
        mPhotoV = (PhotoView) findViewById(R.id.m_photo_pv);
        mBackIv = findViewById(R.id.close_iv);
    }

    @Override
    protected void initListener() {
        //mPhotoV.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_default_img));
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popActivity();
            }
        });
    }

    @Override
    protected void initData() {
        imgPath = getIntent().getStringExtra("imgpath");
        ImageLoadFactory.getInstance().getImageLoadHandler().displayImage(imgPath, mPhotoV);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slid_out_bottom_with_fadein);
    }

}
