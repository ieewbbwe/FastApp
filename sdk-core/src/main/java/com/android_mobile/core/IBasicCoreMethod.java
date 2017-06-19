package com.android_mobile.core;

import android.content.Intent;

/**
 * Created by mxh on 2017/5/31.
 * Describe：Activity基类核心方法
 */

public interface IBasicCoreMethod {
    /**
     * 获取屏幕高度
     */
    float getScreenHeight();

    /**
     * 获取屏幕宽度
     */
    float getScreenWidth();

    /**
     * 获取状态栏高度
     */
    float getStatusBarHeight();

    /**
     * 获取虚拟按键高度
     */
    float getVirtualBarHeight();

    /**
     * 显示加载进度
     */
    void displayProgressBar();

    /**
     * 隐藏加载进度
     */
    void hideProgressBar();

    /**
     * 退出Activity
     */
    void popActivity();

    /**
     * 退出Fragment
     */
    void popFragment();

    /**
     * 跳转Activity
     *
     * @param clazz 类名
     */
    void pushActivity(Class<?> clazz);

    /**
     * 跳转Activity
     *
     * @param intent       意图
     * @param isFinishSelf 是否关闭当前Activity
     */
    void pushActivity(Intent intent, boolean isFinishSelf);

    /**
     * 跳转Activity等待结果
     *
     * @param clazz       类名
     * @param requestCode 请求码
     */
    void pushActivityForResult(Class<?> clazz, int requestCode);

    /**
     * 跳转Activity等待结果
     *
     * @param intent      意图
     * @param requestCode 请求码
     */
    void pushActivityForResult(Intent intent, int requestCode);

    /**
     * toast提示
     *
     * @param resId 资源id
     */
    void toast(int resId);

    void toast(String str);

    /**
     * 设置页面标题
     *
     * @param title 标题
     */
    void setTitle(String title);

    void setTitle(int resId);

}
