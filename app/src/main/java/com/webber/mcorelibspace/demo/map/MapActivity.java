package com.webber.mcorelibspace.demo.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.webber.mcorelibspace.MainActivity;
import com.webber.mcorelibspace.R;

/**
 * 使用地图相关功能之前，请参照sdk-location/README.md 配置地图功能需要的相关信息
 * {@link com.android_mobile.location.LocationHelper}
 */
public class MapActivity extends AppCompatActivity {

    private RecyclerView mMapRv;
    private MainActivity.DemoAdapter demoAdapter;
    private MainActivity.DemoInfo[] DEMOS = {
            new MainActivity.DemoInfo("位置相关功能", "使用百度地图进行定位、距离测算、周边查找", LocationDemoActivity.class),
            new MainActivity.DemoInfo("地图相关功能", "在地图上标注当前坐标，路径规划", MapDemoActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapRv = (RecyclerView) findViewById(R.id.m_map_rv);
        mMapRv.setLayoutManager(new LinearLayoutManager(this));

        demoAdapter = new MainActivity().new DemoAdapter();
        demoAdapter.setData(DEMOS);
        mMapRv.setAdapter(demoAdapter);

    }
}
