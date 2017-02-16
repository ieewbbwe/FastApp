package com.android_mobile.core.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mxh on 2016/10/24.
 * boolean onRVItemLongClick(ViewGroup parent, View itemView, int position);
 */
public interface OnRVLongClickListener {
    boolean onRVItemLongClick(ViewGroup parent, View itemView, int position);
}
