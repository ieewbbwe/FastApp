package com.android_mobile.core.enums;

public enum RequestType {
    POST("post"), GET("get"), PUT("put"), DELETE("delete");
    private String context;

    private RequestType(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }
}
