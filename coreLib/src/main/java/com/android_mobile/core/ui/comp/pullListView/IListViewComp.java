package com.android_mobile.core.ui.comp.pullListView;

import com.android_mobile.core.adapter.BasicRecycleAdapter;

public interface IListViewComp {

    void getListView();

    void setAdapter(BasicRecycleAdapter adapter);

    void setListener();
}
