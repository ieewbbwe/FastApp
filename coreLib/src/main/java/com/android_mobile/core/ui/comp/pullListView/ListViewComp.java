package com.android_mobile.core.ui.comp.pullListView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android_mobile.core.BasicActivity;
import com.android_mobile.core.R;
import com.android_mobile.core.base.BaseComponent;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

/**
 * Created by mxh on 2017/1/20.
 * Describe：
 */

public class ListViewComp extends BaseComponent {

    private LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ILoadMoreViewListener loadListener;
    private SwipeRefreshLayout mSwipeSrl;
    private DividerDecoration divider;

    public ListViewComp(BasicActivity activity, View v) {
        super(activity, v);
    }

    @Override
    public int onCreate() {
        return R.layout.comp_l_recycle_list;
    }

    @Override
    public void initComp() {
        mRecyclerView = (LRecyclerView) findViewById(R.id.m_list_lrv);
        mRecyclerView.setFootViewHint("拼命加载中", "没有更多内容", "网络不给力啊，点击再试一次吧");
    }


    public void setAdapter(RecyclerView.Adapter mDataAdapter) {
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        divider = new DividerDecoration.Builder(activity)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.divide_line)
                .build();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

    }

    public void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void refreshComplete() {
        mRecyclerView.refreshComplete();
    }

    public void loadComplete() {
        mRecyclerView.loadMoreComplete();
    }

    public void setScrollListener(LRecyclerView.LScrollListener listener) {
        mRecyclerView.setLScrollListener(listener);
    }

    public void setIsEnd(boolean isEnd) {
        mRecyclerView.setNoMore(isEnd);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mLRecyclerViewAdapter.setOnItemClickListener(listener);
    }


    public LRecyclerView getRecycleView() {
        return mRecyclerView;
    }

    public LRecyclerViewAdapter getAdapter() {
        return mLRecyclerViewAdapter;
    }

    /**
     * 添加头
     */
    public void addHeader(View header) {
        mLRecyclerViewAdapter.addHeaderView(header);
    }

    public void setListener(ILoadMoreViewListener listener) {
        this.loadListener = listener;
    }

    @Override
    public void initListener() {
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadListener != null) {
                    loadListener.startRefresh();
                }
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (loadListener != null) {
                    loadListener.startLoadMore();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    public void hideDiver() {
        mRecyclerView.removeItemDecoration(divider);
    }
}
