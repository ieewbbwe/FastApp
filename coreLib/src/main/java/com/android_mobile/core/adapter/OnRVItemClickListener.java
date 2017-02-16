package com.android_mobile.core.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Create by mxh on 2016/10/24
 * RecyclerView的item点击事件监听器
 */
public interface OnRVItemClickListener {
    void onRVItemClick(ViewGroup parent, View itemView, int position);
}
