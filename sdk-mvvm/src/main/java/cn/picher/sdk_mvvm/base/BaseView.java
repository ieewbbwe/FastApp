package cn.picher.sdk_mvvm.base;

import android.support.v7.app.AppCompatActivity;

import cn.picher.sdk_mvvm.BasicView;

/**
 * Created by picher on 2018/1/11.
 * Describe：
 */

public interface BaseView extends BasicView {

    void showLoading(boolean show);

    void showError(String error);

    boolean isCanShowDialog();

    AppCompatActivity getSupportActivity();
}
