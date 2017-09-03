package com.webber.mcorelibspace.demo.demos.primary;

import android.util.Log;

/**
 * Created by mxh on 2017/8/14.
 * Describe：
 */

public class ParentClass {

    private String mPrivateFiled;
    public String mFatherName = "father";
    protected int mFatherAge;

    public String getFatherName() {
        return mFatherName;
    }

    private String getPrivateFiled(String addName) {
        Log.d("reflect", "添加的名：" + addName);
        return mPrivateFiled + addName;
    }

    private void privateMethod(String head, int tail) {
        System.out.print(head + tail);
    }

}
