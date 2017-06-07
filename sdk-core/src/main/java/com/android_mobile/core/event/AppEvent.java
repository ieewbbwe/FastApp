package com.android_mobile.core.event;

/**
 * Created by mxh on 2017/6/7.
 * Describe：
 */

public class AppEvent {

    private int code;
    private Object obj;

    public AppEvent(){
    }

    public AppEvent(int code){
        this.code = code;
    }

    public void AppEvent(int code,Object obj){
        this.code = code;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public Object getObj() {
        return obj;
    }

}
