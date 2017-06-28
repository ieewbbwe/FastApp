package com.webber.mcorelibspace.demo.core.router;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android_mobile.core.base.BaseActivity;
import com.webber.mcorelibspace.MainActivity;
import com.webber.mcorelibspace.R;

@Route(path = "/router/routerTest")
public class TestRouterActivity extends BaseActivity {

    @Autowired
    String name;
    @Autowired(name = "psw")
    String password;
    @Autowired
    MainActivity.DemoInfo obj1;
    @Autowired
    TestObj obj2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_router);

        mNavigationBar.hidden();
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {

    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        toast(name + "," + password + "," + (obj1 == null ? "null" : obj1.getTitle()) + "," + (obj2 == null ? "" : obj2.getName()));
    }
}
