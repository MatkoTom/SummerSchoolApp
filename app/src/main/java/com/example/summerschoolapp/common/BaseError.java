package com.example.summerschoolapp.common;

public class BaseError {
    private Object error;
    private String extraInfo;

    public BaseError(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public String toString() {
        return "BaseError{" +
                "error=" + error +
                ", extraInfo='" + extraInfo + '\'' +
                '}';
    }
}
