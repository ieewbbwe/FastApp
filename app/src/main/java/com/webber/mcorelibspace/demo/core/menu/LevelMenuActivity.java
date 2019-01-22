package com.webber.mcorelibspace.demo.core.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.BaseActivity;
import com.webber.mcorelibspace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/router/levelMenu")
public class LevelMenuActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.menu_filter_area_tv)
    TextView menuFilterAreaTv;
    @BindView(R.id.area_iv)
    ImageView areaIv;
    @BindView(R.id.menu_filter_area_rl)
    RelativeLayout menuFilterAreaRl;
    @BindView(R.id.menu_filter_case_type_tv)
    TextView menuFilterCaseTypeTv;
    @BindView(R.id.case_type_iv)
    ImageView caseTypeIv;
    @BindView(R.id.menu_filter_case_type_rl)
    RelativeLayout menuFilterCaseTypeRl;
    @BindView(R.id.menu_filter_sort_tv)
    TextView menuFilterSortTv;
    @BindView(R.id.sort_iv)
    ImageView sortIv;
    @BindView(R.id.menu_filter_sort_rl)
    RelativeLayout menuFilterSortRl;
    @BindView(R.id.menu_filter_ll)
    LinearLayout menuFilterLl;
    @BindView(R.id.m_shown_space)
    View mShadowGroup;
    private CascadingMenuPopWindow areaPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        //初始化一级菜单选择栏
        areaPopWindow = new CascadingMenuPopWindow(this, PopType.POP_AREA);

    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        menuFilterAreaRl.setOnClickListener(this);
        menuFilterCaseTypeRl.setOnClickListener(this);
        menuFilterSortRl.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_filter_area_rl:
                showFilterPopWindow(areaPopWindow, areaIv, menuFilterAreaTv, menuFilterLl);
                break;
            case R.id.menu_filter_case_type_rl:
                showFilterPopWindow(areaPopWindow, caseTypeIv, menuFilterCaseTypeTv, menuFilterLl);
                break;
            case R.id.menu_filter_sort_rl:
                showFilterPopWindow(areaPopWindow, sortIv, menuFilterSortTv, menuFilterLl);
                break;
        }
    }

    /**
     * 显示筛选列表
     *
     * @param v     below to this view
     * @param arrow status indicate arrow
     */
    public void showFilterPopWindow(PopupWindow menuPopWindow, final ImageView arrow, final TextView tv, View v) {
        menuPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv.setSelected(false);
                arrow.setImageResource(R.mipmap.ic_filter_arrow);
                ObjectAnimator.ofFloat(arrow, "rotation", 0).setDuration(500).start();
                ValueAnimator colorAnim = ObjectAnimator.ofInt(mShadowGroup,
                        "backgroundColor", 0x65000000, 0x00000000);
                colorAnim.setDuration(300);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mShadowGroup.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                });
                colorAnim.start();
            }
        });
        tv.setSelected(true);
        arrow.setImageResource(R.mipmap.ic_filter_arrow_select);
        mShadowGroup.setVisibility(View.VISIBLE);
        ValueAnimator colorAnim = ObjectAnimator.ofInt(mShadowGroup,
                "backgroundColor", 0x00000000, 0x65000000);
        colorAnim.setDuration(300);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
        ObjectAnimator.ofFloat(arrow, "rotation", 180).setDuration(500).start();
        menuPopWindow.showAsDropDown(v);
    }
}
