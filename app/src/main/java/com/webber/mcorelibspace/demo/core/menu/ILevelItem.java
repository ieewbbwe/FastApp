package com.webber.mcorelibspace.demo.core.menu;

/**
 * Created by mxh on 2017/8/16.
 * Describe：联级菜单item
 */

public interface ILevelItem {
    /*获取名*/
    String getName();

    /*获取id*/
    String getCode();

    /*获取父类id*/
    String getParentId();
}
