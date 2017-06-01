package com.android_mobile.core.ui.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicPageAdapter<T> extends PagerAdapter {
    protected List<T> list;
    protected Context ctx;

    public BasicPageAdapter(Context context, List<T> list) {
        this.ctx = context;
        this.list = list;
    }

    public BasicPageAdapter(Context context) {
        this.ctx = context;
        this.list = new ArrayList<>();
    }

    public void setList(List<T> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public List<T> getList() {
        return this.list;
    }

    @Override
    public int getCount() {
        if (this.list != null) {
            return this.list.size();
        } else {
            return 0;
        }
    }

    public T getItem(int pos) {
        return this.list.get(pos);
    }
}
