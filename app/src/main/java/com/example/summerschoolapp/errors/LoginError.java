package com.example.summerschoolapp.errors;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;

public class LoginError extends BaseError {

    public static LoginError Create(LoginError.Error error) {
        return new LoginError(error);
    }

    public enum Error {
        ERROR_WHILE_LOGIN_WRONG_EMAIL(R.string.wrong_email),
        ERROR_WHILE_WHILE_LOGIN_WRONG_PASSWORD(R.string.wrong_password),
        SOMETHING_WENT_WRONG(R.string.text_try_again);

        private int value;

        Error(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private LoginError(LoginError.Error error) {
        super(error);
    }
}
