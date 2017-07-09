package com.webber.mcorelibspace.demo.core.databases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.android_mobile.core.R;
import com.android_mobile.core.listener.ISelectItem;
import com.webber.mcorelibspace.demo.core.databases.tableBean.UsageTimer;

/**
 * Created by mxh on 2017/7/4.
 * Describe：只有一条Text的适配器
 */

public class SimpleTextAdapter extends BasicAdapter<UsageTimer, SimpleTextAdapter.ViewHolder> {

    public SimpleTextAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        ISelectItem item = mDataList.get(position);
        holder.mSimpleTv.setText(item.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.view_simple_text_item, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mSimpleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mSimpleTv = (TextView) itemView.findViewById(R.id.m_simple_tv);
        }
    }
}
