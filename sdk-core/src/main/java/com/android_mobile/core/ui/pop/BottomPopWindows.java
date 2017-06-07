package com.android_mobile.core.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_mobile.core.BasicPopWindow;
import com.android_mobile.core.R;

/**
 * Created by mxh on 2017/6/1.
 * Describe：
 */

public class BottomPopWindows extends BasicPopWindow implements View.OnClickListener {

    private View.OnClickListener clickListener;
    private TextView mPhotograph;
    private TextView mAlbums;
    private TextView mCancel;

    public BottomPopWindows(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.view_select_data, parent, false);
    }

    @Override
    public void onViewCreated(View view) {
        mPhotograph = (TextView) findViewById(R.id.tv_take_photo);
        mAlbums = (TextView) findViewById(R.id.tv_pick_photo);
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        setAnimationStyle(R.style.take_photo_anim);

        initListener();
    }

    private void initListener() {
        mPhotograph.setOnClickListener(this);
        mAlbums.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v);
    }
}
