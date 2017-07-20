package com.webber.mcorelibspace;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.webber.mcorelibspace.demo.core.CoreDemoActivity;
import com.webber.mcorelibspace.demo.demos.SomeDemoActivity;
import com.webber.mcorelibspace.demo.map.MapActivity;
import com.webber.mcorelibspace.demo.net.NetDemoActivity;
import com.webber.mcorelibspace.demo.pay.PayDemoActivity;
import com.webber.mcorelibspace.demo.share.ShareActivity;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private RecyclerView mDemoRv;

    private DemoInfo[] DEMOS = {
            new DemoInfo("分享模块", "使用ShareSdk进行分享操作", ShareActivity.class),
            new DemoInfo("地图模块", "使用BaiduApi进行定位，导航，路线规划等功能", MapActivity.class),
            new DemoInfo("支付模块", "支付宝支付、微信支付功能", PayDemoActivity.class),
            new DemoInfo("网络模块", "封装Retrofit，提供网络访问功能", NetDemoActivity.class),
            new DemoInfo("核心模块", "框架核心库，封装了组件基类和常用工具集", CoreDemoActivity.class),
            new DemoInfo("demo", "随手的一些demo", SomeDemoActivity.class),
    };

    public MainActivity() {

    }

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
                Class classes = DEMOS[position].demoClass;
                if (classes != null) {
                    Intent intent;
                    intent = new Intent(MainActivity.this, classes);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "模块还未接入!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdapter.setData(DEMOS);
        mDemoRv.setAdapter(mAdapter);
        getPermission();

        //Log.d("picher", "" + (OkHttpFactory.getOkHttpClient().sslSocketFactory() == null));
    }

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /**
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
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

    public static class DemoInfo implements Serializable {
        private String title;
        private String desc;
        private Class<? extends Activity> demoClass;

        public DemoInfo() {
        }

        public DemoInfo(String title, String desc, Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Class<? extends Activity> getDemoClass() {
            return demoClass;
        }

        public void setDemoClass(Class<? extends Activity> demoClass) {
            this.demoClass = demoClass;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}


