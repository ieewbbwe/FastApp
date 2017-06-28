package com.webber.mcorelibspace.demo.core;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.SelectDataActivity;
import com.webber.mcorelibspace.R;

@Route(path = "/router/selectFile")
public class SelectDemoActivity extends SelectDataActivity {

    private TextView mPathTv;
    private Button mSelectBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_data);
        mNavigationBar.setTitle("选择文件Demo");
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        mPathTv = (TextView) findViewById(R.id.m_data_path_tv);
        mSelectBt = (Button) findViewById(R.id.m_select_bt);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        mSelectBt.setOnClickListener(v -> {
            showBottomChoseView();
        });
        setMediaImageListener(imagePath -> {
            mPathTv.setText(imagePath);
        });
        setMediaPictureListener(imagePath -> {
            mPathTv.setText(imagePath);
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
}
