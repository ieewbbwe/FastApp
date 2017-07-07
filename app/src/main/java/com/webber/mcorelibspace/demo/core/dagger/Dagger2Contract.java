package com.webber.mcorelibspace.demo.core.dagger;

import com.android_mobile.core.base.BaseView;

/**
 * Created by mxh on 2017/7/7.
 * Describe：
 */

public class Dagger2Contract {

    interface View extends BaseView {
        void showMessage(String msg);
    }

}
