package com.android_mobile.net;

/**
 * Created by picher on 2017/12/17.
 * Describe：
 */

public class ApiException extends RuntimeException{

    private String code;
    private String message;

    public ApiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiException(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
