package com.webber.mcorelibspace.demo.core.framwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.utiles.Lg;
import com.webber.mcorelibspace.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = "/router/framwork")
public class FramworkActivity extends BaseActivity {

    @BindView(R.id.m_main_content_fl)
    FrameLayout mMainContentFl;
    @BindView(R.id.m_home_all_rb)
    RadioButton mHomeAllRb;
    @BindView(R.id.m_home_job_rb)
    RadioButton mHomeJobRb;
    @BindView(R.id.menu_panel_rg)
    RadioGroup menuPanelRg;
    private String mCurrentFragmentTag;
    private int currentCheckedId;
    private FragmentManager mFragmentManager;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framwork);
        mNavigationBar.hidden();
        if (savedInstanceState != null) {
            //當頁面被意外回收時，頁面會二次創建，fragment會被緩存
            //再次創建時失去了對之前的fragment的管理通道
            //所以要將所有的fragment都hide,然後顯示出需要顯示的fragment
            hideAllFragments();
            currentCheckedId = savedInstanceState.getInt("curr_fragment_id");
            showFragment(currentCheckedId);
        } else {
            showFragment(0);
        }
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        unbinder = ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        menuPanelRg.setOnCheckedChangeListener(mOnCheckChangeListener);
    }

    /**
     * 切换Tab监听
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String tag = null;
            Fragment fragment = null;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            if (mCurrentFragmentTag != null) {
                Fragment currentFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment);
                }
            }
            switch (checkedId) {
                case R.id.m_home_all_rb:
                    currentCheckedId = 0;
                    tag = Module1Fragment.class.getSimpleName();
                    Fragment menu0 = mFragmentManager.findFragmentByTag(tag);
                    if (menu0 != null) {
                        fragment = menu0;
                    } else {
                        fragment = new Module1Fragment();
                    }
                    break;
                case R.id.m_home_job_rb:
                    currentCheckedId = 1;
                    tag = Module2Fragment.class.getSimpleName();
                    Fragment menu1 = mFragmentManager.findFragmentByTag(tag);
                    if (menu1 != null) {
                        fragment = menu1;
                    } else {
                        fragment = new Module2Fragment();
                    }
                    break;
            }
            if (fragment != null && fragment.isAdded()) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.add(R.id.m_main_content_fl, fragment, tag);
            }
            fragmentTransaction.commit();
            mCurrentFragmentTag = tag;
        }
    };

    /**
     * 控制顯示fragment
     *
     * @param i currentFragmentCode
     */
    private void showFragment(int i) {
        Lg.print("tag_fragment", i + "");
        switch (i) {
            case 0:
                mHomeAllRb.setChecked(true);
                break;
            case 1:
                mHomeJobRb.setChecked(true);
                break;
            default:
                break;
        }
    }

    /**
     * 隱藏所有fragment
     */
    private void hideAllFragments() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }
    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
}
