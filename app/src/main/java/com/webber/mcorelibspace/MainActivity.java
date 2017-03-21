package com.webber.mcorelibspace;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android_mobile.core.ui.comp.pullListView.ListBaseAdapter;
import com.android_mobile.core.utiles.Lg;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.webber.mcorelibspace.dataBinding.DataBindingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NActivity {

    private LRecyclerView mListRv;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private DataAdapter mDataAdapter;
    private List<String> strings;
    private ListView mListLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar.hidden();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void dataBindClick(View v) {
        pushActivity(DataBindingActivity.class);
    }

    class DataAdapter extends ListBaseAdapter<String, DataAdapter.ViewHolder> {
        public DataAdapter(Context context) {
            super(context);
        }

        @Override
        public void onBindItemHolder(ViewHolder holder, int position) {
            holder.mSimpleTv.setText(mDataList.get(position));
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.simple_text_layout, parent, false));
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mSimpleTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mSimpleTv = (TextView) itemView.findViewById(R.id.m_item_tv);
            }
        }
    }

    @Override
    protected void initComp() {
        mDataAdapter = new DataAdapter(this);
        mListRv = (LRecyclerView) findViewById(R.id.m_list_rv);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mListRv.setAdapter(mLRecyclerViewAdapter);
        mListRv.setLayoutManager(new LinearLayoutManager(this));
        strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("" + i);
        }
        mDataAdapter.setDataList(strings);

        mListLv = (ListView) findViewById(R.id.m_list_lv);
        ListAdapter adapter = new ListAdapter(strings);
        mListLv.setAdapter(adapter);

        mListLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Lg.print("andy", "huadong");
            }
        });

        mListRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Lg.print("webber", "onScrollStateChanged");

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Lg.print("webber", "scroll");
            }
        });

    }

    class ListAdapter extends BaseAdapter {

        private List<String> list;

        public ListAdapter(List<String> strings) {
            this.list = strings;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text_layout, parent, false);
            ((TextView) convertView.findViewById(R.id.m_item_tv)).setText(strings.get(position));
            return convertView;
        }
    }

    @Override
    protected void initListener() {
        mListRv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListRv.refreshComplete();
            }
        });

        mListRv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mDataAdapter.addAll(strings);
                mListRv.loadMoreComplete();
            }
        });
    }

    @Override
    protected void initData() {

    }

}
