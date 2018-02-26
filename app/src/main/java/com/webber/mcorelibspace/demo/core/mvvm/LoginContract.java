package com.webber.mcorelibspace.demo.core.mvvm;

import cn.picher.sdk_mvvm.BasicViewModel;
import cn.picher.sdk_mvvm.base.BaseView;

/**
 * Created by picher on 2018/1/11.
 * Describe：
 */

public class LoginContract {

    public interface View extends BaseView {

    }

    public interface ViewModel extends BasicViewModel{
        void onClickLogin();
    }

}
