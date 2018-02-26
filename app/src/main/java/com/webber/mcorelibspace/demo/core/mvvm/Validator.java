package com.webber.mcorelibspace.demo.core.mvvm;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.webber.mcorelibspace.R;

/**
 * Created by picher on 2018/1/11.
 * Describe：
 */

public class Validator {

    public static class Result {
        private boolean valid;
        @StringRes
        private int errorMessage;

        public boolean isValid() {
            return valid;
        }

        public int getErrorMessage() {
            return errorMessage;
        }
    }

    public static Result validateLogin(EmailLoginRequestModelInternal emailLoginRequestModel) {
        Result result = new Result();
        if (emailLoginRequestModel == null) {
            result.valid = false;
        } else if (TextUtils.isEmpty(emailLoginRequestModel.getEmail()) || TextUtils.isEmpty(emailLoginRequestModel.getPassword())) {
            result.errorMessage = R.string.empty_email_or_password_error_message;
            result.valid = false;
        } else {
            result.valid = true;
        }

        return result;
    }
}
