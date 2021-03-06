package com.webber.mcorelibspace.demo.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android_mobile.core.BasicAdapter;
import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.utiles.Lg;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.webber.mcorelibspace.MainActivity.DemoInfo;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.core.router.TestObj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CoreDemoActivity extends BaseActivity {

    @BindView(R.id.m_core_demo_lrv)
    LRecyclerView mCoreDemoLrv;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private List<DemoInfo> DEMOS;
    private CoreAdapter mAdapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_demo);
        mNavigationBar.setTitle("核心库Demo");
    }

    @Override
    protected void initComp() {
        unbinder = ButterKnife.bind(this);

        mAdapter = new CoreAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mCoreDemoLrv.setAdapter(mLRecyclerViewAdapter);
        /*DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.divide_line)
                .build();
        mCoreDemoLrv.setHasFixedSize(true);
        mCoreDemoLrv.addItemDecoration(divider);*/
        mCoreDemoLrv.setFooterViewHint("拼命加载中", "没有更多内容", "网络不给力啊，点击再试一次吧");
        mCoreDemoLrv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initListener() {
        mCoreDemoLrv.setOnRefreshListener(() -> Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .map(aLong -> DEMOS).subscribe(demoInfos -> {
                    mAdapter.setDataList(demoInfos);
                    mCoreDemoLrv.refreshComplete(10);
                }));
        mCoreDemoLrv.setOnLoadMoreListener(() -> Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aLong -> mCoreDemoLrv.setNoMore(false)));
        mLRecyclerViewAdapter.setOnItemClickListener((view, position) -> toast(mAdapter.getItemObject(position).getTitle()));
    }

    @Override
    protected void initData() {
        DEMOS = new ArrayList<>();
        DEMOS.add(new DemoInfo("基础Activity+Fragment框架", "/router/framwork", null));
        DEMOS.add(new DemoInfo("MVVM 基础框架", "/router/mvvmframwork", null));
        DEMOS.add(new DemoInfo("轮播图", "/router/banner", null));
        DEMOS.add(new DemoInfo("路由测试", "/router/routerTest", null));
        DEMOS.add(new DemoInfo("文件选择", "/router/selectFile", null));
        DEMOS.add(new DemoInfo("数据库操作", "/router/databasesDemo", null));
        DEMOS.add(new DemoInfo("7.0 URI测试", "/router/uriTest", null));
        DEMOS.add(new DemoInfo("dagger2", "/router/dagger", null));
        DEMOS.add(new DemoInfo("级联菜单", "/router/levelMenu", null));
        mCoreDemoLrv.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    class CoreAdapter extends BasicAdapter<DemoInfo, CoreAdapter.ViewHolder> {

        public CoreAdapter(Context context) {
            super(context);
        }

        @Override
        public void onBindItemHolder(ViewHolder holder, final int position) {
            DemoInfo item = mDataList.get(position);
            holder.mItemBt.setText(item.getTitle() + "\n" + item.getDesc());
            holder.mItemBt.setOnClickListener(v -> {
                ARouter.getInstance().build(item.getDesc())
                        .withString("name", "mxh")
                        .withString("psw", "123")
                        .withObject("obj1", item)
                        .withObject("obj2", new TestObj("hahaha", "clazz"))
                        .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                        .navigation(CoreDemoActivity.this, new NavigationCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                Lg.print("webber", "onFound");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                Lg.print("webber", "onLost");
                            }
                        });
            });
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.view_demo_item, parent, false));
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.m_item_bt)
            Button mItemBt;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
