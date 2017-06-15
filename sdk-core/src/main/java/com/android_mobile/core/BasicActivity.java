package com.android_mobile.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android_mobile.core.ui.EmptyLayout;
import com.android_mobile.core.ui.NavigationBar;
import com.android_mobile.core.utiles.Lg;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by mxh on 2017/4/26.
 * Describe：Activity基类
 */

public abstract class BasicActivity extends RxAppCompatActivity implements IBasicCoreMethod {

    protected final String TAG = this.getClass().getSimpleName();
    /*设备屏幕宽度*/
    private float SCREEN_WIDTH = -1;
    /*设备屏幕高度*/
    private float SCREEN_HEIGHT = -1;
    /*设备状态栏高度*/
    private float STATUS_BAR_HEIGHT = -1;
    private LinearLayout mProgressBar;
    protected NavigationBar mNavigationBar;
    private EmptyLayout mEmptyLl;
    private RelativeLayout mRootView;
    private View mShadow;
    private ViewStub mBodyStub;
    private View mBodyView;
    private FragmentManager fm;
    private Toast toast;

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        BasicApplication.activityStack.add(this);
        fm = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_frame);
        mNavigationBar = new NavigationBar(this,
                (ViewStub) findViewById(R.id.m_title_bar_vsb));
        mProgressBar = (LinearLayout) findViewById(R.id.m_loading_ll);
        mEmptyLl = (EmptyLayout) findViewById(R.id.error_layout);
        mShadow = findViewById(R.id.m_shade_v);

        mBodyStub = (ViewStub) findViewById(R.id.m_body_vsb);
        mBodyStub.setLayoutResource(layoutResID);
        mBodyView = mBodyStub.inflate();
        mRootView = (RelativeLayout) findViewById(R.id.m_main_root_rl);
        initDefault();
        init();
    }

    public View getRootView() {
        return mRootView;
    }

    private void printLog() {
        Lg.print(TAG, Thread.currentThread().getStackTrace()[3].getMethodName());
    }

    private void initDefault() {
        mNavigationBar.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popFragment();
            }
        });
    }

    protected void init() {
        printLog();
        initComp();
        initListener();
        initData();
    }

    @Override
    public void popActivity() {
        BasicApplication.activityStack.pop().finish();
    }

    @Override
    public void popFragment() {
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            popActivity();
        }
    }

    public void pushActivity(Intent intent) {
        pushActivity(intent, false);
    }

    @Override
    public void pushActivity(Class<?> aClass) {
        pushActivity(new Intent(this, aClass));
    }

    @Override
    public void pushActivity(Intent intent, boolean isFinishSelf) {
        startActivity(intent);
        if (isFinishSelf) {
            popActivity();
        }
    }

    @Override
    public void pushActivityForResult(Class<?> clazz, int requestCode) {
        pushActivityForResult(new Intent(this, clazz), requestCode);
    }

    @Override
    public void pushActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void toast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void toast(String str) {
        if (toast == null) {
            toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    @Override
    public void setTitle(String title) {
        mNavigationBar.setTitle(title);
    }

    public void setTitle(int resId) {
        mNavigationBar.setTitle(resId);
    }

    @Override
    public float getScreenWidth() {
        if (SCREEN_WIDTH == -1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            SCREEN_WIDTH = metrics.widthPixels;
        }
        return SCREEN_WIDTH;
    }

    @Override
    public float getScreenHeight() {
        if (SCREEN_HEIGHT == -1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            SCREEN_HEIGHT = metrics.heightPixels;
        }
        return SCREEN_HEIGHT;
    }

    @Override
    public float getStatusBarHeight() {
        if (STATUS_BAR_HEIGHT == -1) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                STATUS_BAR_HEIGHT = getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return STATUS_BAR_HEIGHT;
    }

    @Override
    public float getVirtualBarHeight() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void displayProgressBar() {
        mProgressBar.bringToFront();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void exitAppWithToast() {
        while (BasicApplication.activityStack.size() > 0) {
            BasicApplication.activityStack.pop().finish();
        }
        finish();
        System.gc();
        System.exit(0);
    }
}
