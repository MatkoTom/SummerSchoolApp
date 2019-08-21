package com.example.summerschoolapp.errors;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;

public class SignupError extends BaseError {

    public static SignupError Create(SignupError.Error error) {
        return new SignupError(error);
    }

    public enum Error {
        ERROR_WHILE_SIGNUP_OIB_IN_USE(R.string.oib_already_in_use),
        ERROR_WHILE_SIGNUP_EMAIL_IN_USE(R.string.email_in_use),
        SOMETHING_WENT_WRONG(R.string.text_try_again);
        private int value;

        Error(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private SignupError(SignupError.Error error) {
        super(error);
    }
}
