package com.webber.mcorelibspace.demo.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.webber.mcorelibspace.MainActivity;
import com.webber.mcorelibspace.R;

public class NetDemoActivity extends AppCompatActivity {

    private MainActivity.DemoInfo[] DEMOS = {
            new MainActivity.DemoInfo("网络请求", "使用GET、POST、DELETE、PUT进行网络请求", NetRequestActivity.class),
            new MainActivity.DemoInfo("文件上传\\下载", "上传单个文件、多个文件", NetUpDownActivity.class),
            new MainActivity.DemoInfo("Https", "支持Https例子", NetHttpsActivity.class),
            new MainActivity.DemoInfo("网络缓存示例", "网络请求，支持离线缓存，数据更新", NetHttpsActivity.class)
    };
    private RecyclerView mNetDemoRlv;
    private MainActivity.DemoAdapter demoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_demo);
        mNetDemoRlv = (RecyclerView) findViewById(R.id.m_net_demo_rlv);
        mNetDemoRlv.setLayoutManager(new LinearLayoutManager(this));
        demoAdapter = new MainActivity.DemoAdapter();
        demoAdapter.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent;
            intent = new Intent(NetDemoActivity.this, DEMOS[position].getDemoClass());
            startActivity(intent);
        });
        demoAdapter.setData(DEMOS);
        mNetDemoRlv.setAdapter(demoAdapter);

    }

}
