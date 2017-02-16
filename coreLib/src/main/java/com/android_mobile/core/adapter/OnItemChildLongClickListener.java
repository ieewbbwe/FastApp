package com.android_mobile.core.adapter;

import android.view.View;
import android.view.ViewGroup;

public interface OnItemChildLongClickListener {
    boolean onItemChildLongClick(ViewGroup parent, View childView, int position);
}
