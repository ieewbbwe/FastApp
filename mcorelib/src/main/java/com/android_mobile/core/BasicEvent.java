package com.android_mobile.core;

/**
 * Created by mxh on 2017/6/1.
 * Describe：RxBus 事件基类
 */

public class BasicEvent {

    private Object obj;
    private int type;

    public Object getObj() {
        return obj;
    }

    public int getType() {
        return type;
    }
}
