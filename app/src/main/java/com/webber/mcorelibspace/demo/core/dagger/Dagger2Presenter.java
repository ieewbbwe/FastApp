package com.webber.mcorelibspace.demo.core.dagger;

/**
 * Created by mxh on 2017/7/7.
 * Describe：
 */

public class Dagger2Presenter implements Dagger2Contract.Presenter {

    private ModuleRepository moduleRepository;
    private Dagger2Contract.View dagger2View;

    public Dagger2Presenter(Dagger2Contract.View view) {
        dagger2View = view;
    }

    @Override
    public void login(String userName, String password) {

        String str;
        if (userName.equals("mxh") && password.equals("123")) {
            str = "验证成功！";
        } else {
            str = "验证失败";
        }
        dagger2View.showMessage(str);
    }
}
