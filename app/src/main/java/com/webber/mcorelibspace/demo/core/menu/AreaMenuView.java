package com.webber.mcorelibspace.demo.core.menu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.webber.mcorelibspace.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 联动ListView
 */
public class AreaMenuView extends LinearLayout {
    /*查询城市成功*/
    private static final int SEARCH_CITY_SUCCEED = 0x01;

    private static final String TAG = "AreaMenuView";
    private PopType type;
    // 菜单选择后触发的接口，即最终选择的内容
    private OnAreaSelectListener mOnSelectListener;
    public ListView firstMenuListView;
    public ListView secondMenuListView;

    // 每次选择的子菜单内容
    private List<SimpleLevelItem> secondItem = new ArrayList<>();
    private List<SimpleLevelItem> menuItem = new ArrayList<>();

    private MenuItemAdapter firstMenuListViewAdapter;

    private MenuItemAdapter secondMenuListViewAdapter;

    private int firstPosition = 0;
    private int secondPosition = 0;
    private String nowParentCode = "-1";
    //private BaseDataDao baseDao;

    private Context context;

    //TODO 使用Handler内存泄露问题
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEARCH_CITY_SUCCEED:
                    if (secondItem != null) {
                        Lg.print(TAG, "secondItemSize:" + secondItem.size());
                    }
                    // 通知适配器刷新
                    secondMenuListViewAdapter.notifyDataSetChanged();
                    secondMenuListViewAdapter.setSelectedPositionNoNotify(
                            0, secondItem);
                    break;
            }
        }
    };
    private ExecutorService executors;
    private boolean isRun = true;

    /**
     * @param context 上下文
     */
    public AreaMenuView(Context context) {
        this(context, null);
    }

    public AreaMenuView(Context context, PopType popType) {
        this(context, null, popType);
    }

    public AreaMenuView(Context context, AttributeSet attrs, PopType popType) {
        super(context, attrs);
        this.context = context;
        this.type = popType;
        //baseDao = new BaseDataDao(context);
        init(context);
    }

    public void setStatus(boolean isRun) {
        this.isRun = isRun;
        menuItem.clear();
        initData();
        if (firstMenuListViewAdapter != null) {
            firstMenuListViewAdapter.notifyDataSetChanged();
            firstMenuListViewAdapter.setSelectedPositionNoNotify(0, menuItem);
        }
    }

    private void init(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_pop_view, this);
        firstMenuListView = (ListView) findViewById(R.id.listView);
        secondMenuListView = (ListView) findViewById(R.id.listView2);
        executors = Executors.newSingleThreadExecutor();
        initData();

        initFirstMenu();
        initSecondMenu();
        //设置默认选择
        setDefaultSelect();
    }

    //查询一级菜单数据
    private void initData() {
        SimpleLevelItem iLevelItem = new SimpleLevelItem("全国", "0", "0");
        SimpleLevelItem iLevelItem1 = new SimpleLevelItem("湖北", "001", "0");
        SimpleLevelItem iLevelItem2 = new SimpleLevelItem("湖南", "002", "0");
        SimpleLevelItem iLevelItem3 = new SimpleLevelItem("河北", "003", "0");
        menuItem.add(iLevelItem);
        menuItem.add(iLevelItem1);
        menuItem.add(iLevelItem2);
        menuItem.add(iLevelItem3);
    }

    /**
     * 初始化一级菜单
     */
    private void initFirstMenu() {
        firstMenuListViewAdapter = new MenuItemAdapter(context, menuItem,
                R.drawable.menu_filter_select,
                R.drawable.selector_choose_item);
        firstMenuListViewAdapter.setTextSize(16);
        firstMenuListViewAdapter.setSelectedPositionNoNotify(firstPosition,
                menuItem);
        firstMenuListView.setAdapter(firstMenuListViewAdapter);
        firstMenuListViewAdapter
                .setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0) {
                            ILevelItem item = menuItem.get(position);
                            if (mOnSelectListener != null) {
                                mOnSelectListener.getValue(item);
                            }
                        }
                        // 选择主菜单，清空原本子菜单内容，增加新内容
                        if (!menuItem.get(position).getCode().equals(nowParentCode)) {
                            secondItem.clear();
                            nowParentCode = menuItem.get(position).getCode();
                            getSecondItem(nowParentCode);
                        }
                     /*   secondItem = getSecondItem(menuItem.get(position)
                                .getCode());*/

                    }
                });
    }

    /**
     * 初始化二级菜单
     */
    private void initSecondMenu() {
        if (!CollectionUtils.isEmpty(menuItem)) {
            //secondItem = getSecondItem(menuItem.get(firstPosition).getCode());
            //   if (!CollectionUtils.isEmpty(secondItem)) {
            secondMenuListViewAdapter = new MenuItemAdapter(context, secondItem,
                    R.drawable.selector_choose_item,
                    R.drawable.selector_choose_item);
            secondMenuListViewAdapter.setTextSize(16);
            secondMenuListViewAdapter.setSelectedPositionNoNotify(secondPosition,
                    secondItem);
            secondMenuListView.setAdapter(secondMenuListViewAdapter);
            secondMenuListViewAdapter
                    .setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, final int position) {
                            // 选择后返回结果
                            ILevelItem menuItem = secondItem.get(position);
                            if (mOnSelectListener != null) {
                                mOnSelectListener.getValue(menuItem);
                            }
                            Lg.print(TAG, menuItem.toString());
                        }
                    });
            // }
        }
    }

    public void getSecondItem(final String pcode) {
        //二级菜单查询市
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //secondItem = baseDao.getCityByProvinceId(pcode, isRun);
                SimpleLevelItem iLevelItem = new SimpleLevelItem("武汉", "0101", "001");
                SimpleLevelItem iLevelItem1 = new SimpleLevelItem("十堰", "0102", "001");
                SimpleLevelItem iLevelItem2 = new SimpleLevelItem("荆州", "0103", "001");
                SimpleLevelItem iLevelItem3 = new SimpleLevelItem("孝感", "0104", "001");
                SimpleLevelItem iLevelItem4 = new SimpleLevelItem("襄阳", "0105", "001");
                secondItem.add(iLevelItem);
                secondItem.add(iLevelItem1);
                secondItem.add(iLevelItem2);
                secondItem.add(iLevelItem3);
                secondItem.add(iLevelItem4);
                handler.sendEmptyMessage(SEARCH_CITY_SUCCEED);
            }
        };
        executors.execute(runnable);
    }

    /**
     * 设置默认选中项
     */
    public void setDefaultSelect() {
        //firstMenuListView.setSelection(firstPosition);
        secondMenuListView.setSelection(secondPosition);
    }

    /**
     * 选择监听
     *
     * @param onSelectListener
     */
    public void setOnAreaSelectListener(OnAreaSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnAreaSelectListener {
        void getValue(ILevelItem item);
    }

    public void notifySecondAdapter() {
        secondMenuListViewAdapter.notifyDataSetChanged();
    }

    //解决内存泄露
    public static class MySearchHandler extends Handler {
        private final WeakReference<AreaMenuView> v;

        public MySearchHandler(AreaMenuView view) {
            this.v = new WeakReference<AreaMenuView>(view);
        }

        /*查询城市成功*/
        private static final int SEARCH_CITY_SUCCEED = 0x01;
        /*查询子案件类型成功*/
        private static final int SEARCH_CASE_TYPE_SUCCEED = 0x02;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEARCH_CITY_SUCCEED:

                    break;
                case SEARCH_CASE_TYPE_SUCCEED:

                    break;
            }
        }
    }

}
