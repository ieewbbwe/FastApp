package com.android_mobile.location;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.baidu.mapapi.map.MapView;

/**
 * Created by mxh on 2017/6/8.
 * Describe：封装mapView
 */

public class MMapView extends FrameLayout {

    private MapView mMapView;

    public MMapView(Context context) {
        this(context, null);
    }

    public MMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mMapView = new MapView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mMapView, params);
    }
}
