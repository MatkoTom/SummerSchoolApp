package com.example.summerschoolapp.model.newRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

public class NewRequestInnerResponse extends BaseModel {
    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseNewRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}
