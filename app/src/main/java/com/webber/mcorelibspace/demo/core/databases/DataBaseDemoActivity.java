package com.webber.mcorelibspace.demo.core.databases;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.utiles.Utiles;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.core.databases.tableBean.UsageTimer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Route(path = "/router/databasesDemo")
public class DataBaseDemoActivity extends BaseActivity {

    @BindView(R.id.m_insert_bt)
    Button mInsertBt;
    @BindView(R.id.m_search_bt)
    Button mSearchBt;
    @BindView(R.id.m_data_rlv)
    RecyclerView mDataRv;
    private DataDao mDataDao;
    private List<UsageTimer> usageTimers = new ArrayList<>();
    private SimpleTextAdapter mAdapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_demo);

    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        unbinder =  ButterKnife.bind(this);
        mDataDao = new DataDao(this);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        mInsertBt.setOnClickListener(v -> {
            mDataDao.insertUsage(new UsageTimer("" + Utiles.getAppUid(getThisContext()),
                    "" + Utiles.getAppVersionCode(getThisContext()), System.currentTimeMillis()));
        });
        mSearchBt.setOnClickListener(v -> {
            Observable.create((Observable.OnSubscribe<List<UsageTimer>>) subscriber -> {
                usageTimers = mDataDao.queryAllUsage();
                subscriber.onNext(usageTimers);
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(usageTimers1 -> {
                        mAdapter.setDataList(usageTimers1);
                    });
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mDataRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mAdapter = new SimpleTextAdapter(this);
        mAdapter.setDataList(usageTimers);
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
