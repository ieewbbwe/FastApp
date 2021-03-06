package com.webber.mcorelibspace.demo.demos.fourcomponent;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.android_mobile.core.utiles.Lg;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.demos.DemoService;
import com.webber.mcorelibspace.demo.demos.SomeDemoActivity;

public class FourComponentActivity extends AppCompatActivity {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_component);

        serviceIntent = new Intent(FourComponentActivity.this, DemoService.class);
        findViewById(R.id.m_service_bt).setOnClickListener(v -> {
            bindService(serviceIntent, serviceConn, BIND_AUTO_CREATE);
        });
        findViewById(R.id.m_stop_service_bt).setOnClickListener(v -> {
            unbindService(serviceConn);
        });
    }

    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Lg.print("picher", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Lg.print("picher", "onServiceDisconnected");
        }
    };

}
