package com.webber.mcorelibspace.demo.core.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.webber.mcorelibspace.R;


/**
 * Create by mxh on 2016/4/26
 * 联级菜单统一管理类
 */
public class CascadingMenuPopWindow extends PopupWindow {

    private PopType type;
    private Context context;
    public AreaMenuView areaMenuView;
    public AreaMenuView caseTypeMenuView;
    public AreaMenuView parentCaseTypeMenuView;

    private CascadingMenuViewOnSelectListener menuViewOnSelectListener;

    //回调接口
    public void setMenuViewOnSelectListener(
            CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
        this.menuViewOnSelectListener = menuViewOnSelectListener;
    }

    public CascadingMenuPopWindow(Context context, PopType type) {
        super(context);
        this.context = context;
        this.type = type;
        init();
    }

    /**
     * 设置是否为运行的城市
     *
     * @param isRun true is run;falseis not
     */
    public void setAreaStatus(boolean isRun) {
        areaMenuView.setStatus(isRun);
    }

    public void init() {
        if (PopType.POP_AREA == type) {//地区，考虑到可能会三级联动，因此单独分出来一个view,以后拓展
            areaMenuView = new AreaMenuView(context,PopType.POP_AREA);
            setContentView(areaMenuView);
            areaMenuView.setOnAreaSelectListener(new AreaMenuView.OnAreaSelectListener() {
                @Override
                public void getValue(ILevelItem item) {
                    if (menuViewOnSelectListener != null) {
                        menuViewOnSelectListener.getFilterSelectValue(item, PopType.POP_AREA);
                        dismiss();
                    }
                }
            });
        } else if (PopType.POP_CASE_TYPE == type) {//案件类型
            caseTypeMenuView = new AreaMenuView(context,PopType.POP_CASE_TYPE);
            setContentView(caseTypeMenuView);
            caseTypeMenuView.setOnAreaSelectListener(new AreaMenuView.OnAreaSelectListener() {
                @Override
                public void getValue(ILevelItem item) {
                    if (menuViewOnSelectListener != null) {
                        menuViewOnSelectListener.getFilterSelectValue(item, PopType.POP_CASE_TYPE);
                        dismiss();
                    }
                }
            });
        } else if (PopType.SIMPLE_POP_CASE_TYPE == type) {
            parentCaseTypeMenuView = new AreaMenuView(context);
            setListWeight(parentCaseTypeMenuView.secondMenuListView, 0);
            setContentView(parentCaseTypeMenuView);
            caseTypeMenuView.setOnAreaSelectListener(new AreaMenuView.OnAreaSelectListener() {
                @Override
                public void getValue(ILevelItem item) {
                    if (menuViewOnSelectListener != null) {
                        menuViewOnSelectListener.getFilterSelectValue(item, PopType.SIMPLE_POP_CASE_TYPE);
                        dismiss();
                    }
                }
            });
        }
        initConfig();
    }

    /**
     * 设置listView的宽度
     *
     * @param weight
     */
    public void setListWeight(ListView lv, int weight) {
        if (lv != null) {
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) lv.getLayoutParams();
            p.weight = weight;
            lv.setLayoutParams(p);
        }
    }

    /**
     * 初始化pop配置信息
     */
    private void initConfig() {
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int windowHeight = metrics.heightPixels;

        setHeight((int) (windowHeight * 0.6));
        setWidth(AbsListView.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.pop_anim);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
    }
}
