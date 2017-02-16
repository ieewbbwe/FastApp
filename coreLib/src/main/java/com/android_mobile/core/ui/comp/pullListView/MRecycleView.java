package com.android_mobile.core.ui.comp.pullListView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mxh on 2016/10/26.
 * Describe：根据位置改变item布局
 */
public class MRecycleView extends RecyclerView {

    /**
     * item 类型
     */
    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_HEADER = 1;//头部--支持头部增加一个headerView
    public final static int TYPE_FOOTER = 2;//底部--往往是loading_more
    public final static int TYPE_LIST = 3;//代表item展示的模式是list模式
    public final static int TYPE_STAGGER = 4;//代码item展示模式是网格模式
    public static final int TYPE_GRID = 5;//代码item展示模式GRIDE模式
    private AutoLoadAdapter mAutoLoadAdapter;

    public MRecycleView(Context context) {
        this(context, null);
    }

    public MRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAutoLoadAdapter = new AutoLoadAdapter(adapter);
        }
        super.swapAdapter(mAutoLoadAdapter, true);
    }

    /**
     * 头尾的Adapter
     */
    public class AutoLoadAdapter extends RecyclerView.Adapter {

        private Adapter mInternalAdapter;
        private boolean mIsHeaderEnable;
        private boolean mIsFooterEnable;
        private View mHeaderV;
        private View mFooterV;

        public AutoLoadAdapter(Adapter adapter) {
            mInternalAdapter = adapter;
            mIsHeaderEnable = false;
            mIsFooterEnable = false;
        }

        @Override
        public int getItemViewType(int position) {
            int headerPosition = 0;
            int footerPosition = getItemCount() - 1;
            if (headerPosition == position && mIsHeaderEnable && mHeaderV != null) {
                return TYPE_HEADER;
            } else if (footerPosition == position && mIsFooterEnable && mFooterV != null) {
                return TYPE_FOOTER;
            }
            /**
             * 这么做保证layoutManager切换之后能及时的刷新上对的布局
             */
            if (getLayoutManager() instanceof GridLayoutManager) {
                return TYPE_GRID;
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                return TYPE_STAGGER;
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                return TYPE_LIST;
            } else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER && mHeaderV != null) {
                return new HeaderViewHolder(mHeaderV);
            } else if (viewType == TYPE_FOOTER && mFooterV != null) {
                return new FooterViewHolder(mHeaderV);
            } else {
                return mInternalAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type != TYPE_FOOTER && type != TYPE_HEADER) {
                mInternalAdapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            int count = mInternalAdapter.getItemCount();
            if (mIsHeaderEnable) {
                count++;
            }
            if (mIsFooterEnable) {
                count++;
            }
            return count;
        }

        public void setHeaderEnable(boolean enable) {
            mIsHeaderEnable = enable;
        }

        public void addHeaderView(int resId) {
            mHeaderV = LayoutInflater.from(getContext()).inflate(resId, null);
        }

        public void addHeaderView(View v) {
            mHeaderV = v;
        }

        public void setFooterEnable(boolean enable) {
            mIsFooterEnable = enable;
        }

        public void addFooterView(int resId) {
            mFooterV = LayoutInflater.from(getContext()).inflate(resId, null);
        }

        public void addFooterView(View v) {
            mFooterV = v;
        }

        public class FooterViewHolder extends ViewHolder {
            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }

        public class HeaderViewHolder extends ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
