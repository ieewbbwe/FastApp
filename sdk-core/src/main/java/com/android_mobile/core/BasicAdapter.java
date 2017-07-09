package com.android_mobile.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.android_mobile.core.utiles.CollectionUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecycleView.Adapter 基类
 *
 * @param <T> 数据类型
 * @param <M> ViewHolder
 */
public abstract class BasicAdapter<T, M extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<M> {
    protected Context mContext;
    protected LayoutInflater mInflater;

    protected List<T> mDataList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public BasicAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(M holder, final int position) {
        onBindItemHolder(holder, position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public abstract void onBindItemHolder(M holder, int position);

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if (position != (getDataList().size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mDataList.size() - position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public T getItemObject(int position) {
        if (CollectionUtils.isNotEmpty(mDataList)) {
            return mDataList.get(position);
        }
        return null;
    }

}
