package com.webber.mcorelibspace.demo.core.menu;

/**
 * Created by mxh on 2017/8/16.
 * Describe：级联菜单简单对象
 */

public class SimpleLevelItem implements ILevelItem {

    private String name;
    private String code;
    private String parentId;

    public SimpleLevelItem() {
    }

    public SimpleLevelItem(String name, String code, String parentId) {
        this.name = name;
        this.code = code;
        this.parentId = parentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getParentId() {
        return parentId;
    }
}
