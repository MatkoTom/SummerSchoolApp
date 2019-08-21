package com.example.summerschoolapp.errors;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;

public class AuthError extends BaseError {

    public static AuthError Create(AuthError.Error error) {
        return new AuthError(error);
    }

    public enum Error {
        UNATUHORISED(R.string.error_unauthorised);

        private int value;

        Error(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private AuthError(AuthError.Error error) {
        super(error);
    }
}
