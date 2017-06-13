package com.android_mobile.net.response;

import java.io.Serializable;
import java.net.HttpURLConnection;

/**
 * Created by mxh on 2016/11/22.
 * Describe：响应基类
 */

public class BaseResponse implements Serializable {

    /*响应信息*/
    private String message;
    /*响应码*/
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_CREATED
                || code == HttpURLConnection.HTTP_ACCEPTED || code == HttpURLConnection.HTTP_NOT_AUTHORITATIVE;
    }
}
