package com.webber.mcorelibspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.android_mobile.location.MMapView;
import com.webber.mcorelibspace.demo.map.MapActivity;
import com.webber.mcorelibspace.demo.pay.PayDemoActivity;
import com.webber.mcorelibspace.demo.share.ShareActivity;

import cn.sharesdk.SocialShareHelper;


public class MainActivity extends Activity {

    private SocialShareHelper socialComponent;
    private MMapView mMapView;
    private RecyclerView mDemoRv;

    private DemoInfo[] DEMOS = {
            new DemoInfo("分享模块", "使用ShareSdk进行分享操作", ShareActivity.class),
            new DemoInfo("地图模块", "使用BaiduApi进行定位，导航，路线规划等功能", MapActivity.class),
            new DemoInfo("支付模块", "支付宝支付、微信支付功能", PayDemoActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDemoRv = (RecyclerView) findViewById(R.id.m_demo_rv);
        mDemoRv.setLayoutManager(new LinearLayoutManager(this));
        DemoAdapter mAdapter = new DemoAdapter();
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("webber", "" + position);
                Intent intent;
                intent = new Intent(MainActivity.this, DEMOS[position].demoClass);
                startActivity(intent);
            }
        });
        mAdapter.setData(DEMOS);
        mDemoRv.setAdapter(mAdapter);
    }

    public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

        private AdapterView.OnItemClickListener listener;
        private DemoInfo[] demoInfos;

        public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setData(DemoInfo[] demoInfos) {
            this.demoInfos = demoInfos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_demo_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mDemoBt.setText(demoInfos[position].title + "\n" + demoInfos[position].desc);

            if (listener != null) {
                Log.d("webber", "设置监听" + position);
                holder.mDemoBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(null, v, position, 0);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return demoInfos.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private Button mDemoBt;

            public ViewHolder(View itemView) {
                super(itemView);
                mDemoBt = (Button) itemView.findViewById(R.id.m_item_bt);
            }
        }
    }

    public static class DemoInfo {
        private String title;
        private String desc;
        private Class<? extends Activity> demoClass;

        public DemoInfo(String title, String desc, Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
