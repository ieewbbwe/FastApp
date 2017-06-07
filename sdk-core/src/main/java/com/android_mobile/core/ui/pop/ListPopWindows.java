package com.android_mobile.core.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android_mobile.core.BasicPopWindow;
import com.android_mobile.core.R;
import com.android_mobile.core.listener.ISelectItem;

import java.util.List;


public class ListPopWindows extends BasicPopWindow {

    private final List<ISelectItem> list;
    private final Context ctx;
    private ListView mList;
    private onSortClickListener sortClickListener;
    private ListPopAdapter listPopAdapter;

    public ListPopWindows(Context context, List<ISelectItem> list) {
        super(context);
        this.ctx = context;
        this.list = list;
        initData();
    }

    private void initData() {
        listPopAdapter = new ListPopAdapter(ctx, list);
        int currentPosition = 0;
        listPopAdapter.setSelectPosition(currentPosition);
        mList.setAdapter(listPopAdapter);
        mList.setSelection(currentPosition);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sortClickListener != null) {
                    sortClickListener.onSortItemClick(position);
                    listPopAdapter.setSelectPosition(position);
                    dismiss();
                }
            }
        });
    }

    public void setSortData(List<ISelectItem> list) {
        if (list == null)
            return;
        listPopAdapter.setList(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.view_pop_list, parent, false);
    }

    @Override
    public void onViewCreated(View view) {
        mList = (ListView) view.findViewById(R.id.pop_list_lv);
    }

    public void setOnSortClickListener(onSortClickListener l) {
        this.sortClickListener = l;
    }

    public interface onSortClickListener {
        public void onSortItemClick(int sortPos);
    }
}
