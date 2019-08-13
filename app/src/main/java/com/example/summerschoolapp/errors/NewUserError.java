package com.example.summerschoolapp.errors;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;

public class NewUserError extends BaseError {

    public static NewUserError Create(NewUserError.Error error) {
        return new NewUserError(error);
    }

    public enum Error {
        ERROR_WHILE_REGISTERING_OIB_IN_USE(R.string.oib_already_in_use),
        ERROR_WHILE_REGISTERING_EMAIL_IN_USE(R.string.email_in_use),
        SOMETHING_WENT_WRONG(R.string.text_try_again);

        private int value;

        Error (int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public NewUserError(NewUserError.Error error) {
        super(error);
    }
}
