package com.android_mobile.core.ui.comp.dialog;

import android.content.DialogInterface;

/**
 * Created by mxh on 2016/10/26.
 * Describe：
 */
public interface IDialogComp {

    void show();

    void setTitle(String title);

    void setContent(String content);

    void setButtonName(String positive, String negative);

    void setOnNegativeClickListener(DialogInterface listener);

    void setOnPositiveClickListener(DialogInterface listener);

    void hideViewById(int viewId);
}
