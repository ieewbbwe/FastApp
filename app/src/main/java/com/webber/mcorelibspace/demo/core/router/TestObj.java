package com.webber.mcorelibspace.demo.core.router;

/**
 * Created by mxh on 2017/6/22.
 * Describe：
 */

public class TestObj{
    private String name;
    private String clazz;

    public TestObj(){}

    public TestObj(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
