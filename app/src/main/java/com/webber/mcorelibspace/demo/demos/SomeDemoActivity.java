package com.webber.mcorelibspace.demo.demos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.webber.mcorelibspace.MainActivity.DemoAdapter;
import com.webber.mcorelibspace.MainActivity.DemoInfo;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.demos.fourcomponent.FourComponentActivity;
import com.webber.mcorelibspace.demo.demos.primary.ReflectActivity;

public class SomeDemoActivity extends AppCompatActivity {

    private RecyclerView mSomeDemosRv;
    private DemoInfo[] DEMOS = {
            new DemoInfo("四大组件", "Activity、Service、BroadcastReceiver、ContentProvider", FourComponentActivity.class),
            new DemoInfo("Java反射", "Java Reflect", ReflectActivity.class),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some_demo);
        mSomeDemosRv = (RecyclerView) findViewById(R.id.m_some_demo_rv);
        mSomeDemosRv.setLayoutManager(new LinearLayoutManager(this));
        DemoAdapter mAdapter = new DemoAdapter();
        mAdapter.setOnItemClickListener((parent, view, position, id) -> {
            Class classes = DEMOS[position].demoClass;
            if (classes != null) {
                Intent intent;
                intent = new Intent(SomeDemoActivity.this, classes);
                startActivity(intent);
            } else {
                Toast.makeText(SomeDemoActivity.this, "模块还未接入!", Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setData(DEMOS);
        mSomeDemosRv.setAdapter(mAdapter);

    }

}
