package com.android_mobile.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mxh on 2016/10/24.
 * 适用于RecyclerView的item的ViewHolder
 */
public class BasicRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    protected Context mContext;
    protected final RecyclerView mRecyclerView;
    protected final OnRVLongClickListener mOnRVItemLongClickListener;
    protected final OnRVItemClickListener mOnRVItemClickListener;
    protected final ViewHolderHelper mViewHolderHelper;

    public BasicRecycleViewHolder(RecyclerView recyclerView, View itemView, OnRVItemClickListener onRVItemClickListener, OnRVLongClickListener onRVLongClickListener) {
        super(itemView);
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mOnRVItemLongClickListener = onRVLongClickListener;
        mOnRVItemClickListener = onRVItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mViewHolderHelper = new ViewHolderHelper(mRecyclerView, itemView);
        mViewHolderHelper.setRecyclerViewHolder(this);
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == this.itemView.getId() && null != mOnRVItemLongClickListener) {
            return mOnRVItemLongClickListener.onRVItemLongClick(mRecyclerView, v, getAdapterPosition());
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.itemView.getId() && null != mOnRVItemClickListener) {
            mOnRVItemClickListener.onRVItemClick(mRecyclerView, v, getAdapterPosition());
        }
    }

    public ViewHolderHelper getViewHolderHelper() {
        return mViewHolderHelper;
    }
}
