package com.webber.mcorelibspace.demo.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android_mobile.location.MMapView;
import com.baidu.mapapi.model.LatLng;
import com.webber.mcorelibspace.R;

public class MapDemoActivity extends AppCompatActivity {

    private MMapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        mMapView = (MMapView) findViewById(R.id.m_demo_mmv);
        mMapView.moveToPoint(new LatLng(30.5656, 114.321288), false);
        findViewById(R.id.m_locate_current_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapView.tagCurrentPosInMap();
            }
        });
    }
}
