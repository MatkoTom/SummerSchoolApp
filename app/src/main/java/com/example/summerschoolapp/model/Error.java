package com.example.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("error_code")
    private String error_code;

    @SerializedName("error_description")
    private String error_description;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    @Override
    public String toString() {
        return "Error{" +
                "error_code='" + error_code + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
