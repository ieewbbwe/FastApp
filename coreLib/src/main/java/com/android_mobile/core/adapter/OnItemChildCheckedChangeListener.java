package com.android_mobile.core.adapter;

import android.view.ViewGroup;
import android.widget.CompoundButton;

public interface OnItemChildCheckedChangeListener {
    void onItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked);
}
