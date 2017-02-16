package com.android_mobile.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android_mobile.core.utiles.Lg;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicRecycleAdapter<M> extends RecyclerView.Adapter<BasicRecycleViewHolder> {

    public static final int STATE_EMPTY_ITEM = 0;
    /**
     * 加载更多
     */
    public static final int STATE_LOAD_MORE = 1;
    /**
     * 没有更多
     */
    public static final int STATE_NO_MORE = 2;
    /**
     * 无数据
     */
    public static final int STATE_NO_DATA = 3;
    /**
     * 数据不满一页
     */
    public static final int STATE_LESS_ONE_PAGE = 4;
    /**
     * 网络错误
     */
    public static final int STATE_NETWORK_ERROR = 5;
    /**
     * 其他状态
     */
    public static final int STATE_OTHER = 6;

    private int mState;
    protected final int mItemLayoutId;
    protected RecyclerView mRecyclerView;
    protected Context mContext;
    protected List<M> mData;
    private OnRVItemClickListener mOnRVItemClickListener;
    private OnRVLongClickListener mOnRVItemLongClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;

    public BasicRecycleAdapter(RecyclerView recyclerView, int itemLayoutId) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mItemLayoutId = itemLayoutId;
        mData = new ArrayList<>();
    }

    @Override
    public BasicRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BasicRecycleViewHolder viewHolder;
        if (mState == STATE_LOAD_MORE) {
            Lg.print("loadList", "加载更多");
        } else if (mState == STATE_NO_MORE) {
            Lg.print("loadList", "没有更多");
        } else {

        }
        viewHolder = new BasicRecycleViewHolder(mRecyclerView, LayoutInflater.from(mContext)
                .inflate(mItemLayoutId, parent, false), mOnRVItemClickListener, mOnRVItemLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(mOnItemChildCheckedChangeListener);
        setItemChildListener(viewHolder.getViewHolderHelper());

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(BasicRecycleViewHolder holder, int position) {
        fillData(holder.getViewHolderHelper(), position, getItem(position));
    }

    /**
     * 填充item数据
     *
     * @param viewHolderHelper
     * @param position
     * @param model
     */
    protected abstract void fillData(ViewHolderHelper viewHolderHelper, int position, M model);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 设置item的点击事件监听器
     *
     * @param onRVItemClickListener
     */
    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener) {
        mOnRVItemClickListener = onRVItemClickListener;
    }

    /**
     * 设置item的长按事件监听器
     *
     * @param onRVItemLongClickListener
     */
    public void setOnRVItemLongClickListener(OnRVLongClickListener onRVItemLongClickListener) {
        mOnRVItemLongClickListener = onRVItemLongClickListener;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置item中的子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 设置item子控件选中状态变化事件监听器
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(OnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以这里采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param viewHolderHelper
     */
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
    }

    public M getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<M> getData() {
        return mData;
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(List<M> data) {
        if (data != null) {
            mData.addAll(0, data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(List<M> data) {
        if (data != null) {
            mData.addAll(mData.size(), data);
            notifyItemRangeInserted(mData.size(), data.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(List<M> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        if (position != -1) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mData.add(position, model);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        addItem(mData.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        notifyItemChanged(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setState(int state) {
        this.mState = state;
    }

}
