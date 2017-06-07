package com.android_mobile.core.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.R;
import com.android_mobile.core.listener.ISelectItem;

import java.util.List;


/**
 * 列表pop适配器
 */
public class ListPopAdapter extends BaseAdapter {

    private List<ISelectItem> list;
    private final Context ctx;
    private int selectIndex = 0;

    public ListPopAdapter(Context ctx, List<ISelectItem> list) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.view_pop_list_item, parent, false);
            vh = new ViewHolder();
            vh.mSortTv = (TextView) convertView.findViewById(R.id.sort_name_tv);
            vh.mCheckIv = (ImageView) convertView.findViewById(R.id.list_pop_check_right_iv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mSortTv.setText(list.get(position).getName());
        if (selectIndex == position) {
            vh.mCheckIv.setVisibility(View.VISIBLE);
            vh.mSortTv.setTextColor(ctx.getResources().getColor(R.color.menu_filter_text_select_color));
        } else {
            vh.mCheckIv.setVisibility(View.GONE);
            vh.mSortTv.setTextColor(ctx.getResources().getColor(R.color.menu_item_text_color));
        }
        return convertView;
    }

    public class ViewHolder {
        TextView mSortTv;
        ImageView mCheckIv;
    }

    /**
     * 设置选中项
     *
     * @param position selected index
     */
    public void setSelectPosition(int position) {
        this.selectIndex = position;
        notifyDataSetChanged();
    }

    /**
     * 获取选中位置
     *
     * @return selectIndex
     */
    public int getSelectPosition() {
        if (list != null && selectIndex < list.size()) {
            return selectIndex;
        }
        return -1;
    }

    public void setList(List<ISelectItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
