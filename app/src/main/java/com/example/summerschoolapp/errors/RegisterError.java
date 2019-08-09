package com.example.summerschoolapp.errors;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;

public class RegisterError extends BaseError {

    public static RegisterError Create(RegisterError.Error error) {
        return new RegisterError(error);
    }

    public enum Error {
        ERROR_WHILE_REGISTERING(R.string.oib_already_in_use),
        SOMETHING_WENT_WRONG(R.string.text_try_again);

        private int value;

        Error(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private RegisterError(RegisterError.Error error) {
        super(error);
    }
}
