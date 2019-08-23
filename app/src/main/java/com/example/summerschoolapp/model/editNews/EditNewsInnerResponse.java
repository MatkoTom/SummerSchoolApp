package com.example.summerschoolapp.model.editNews;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

public class EditNewsInnerResponse extends BaseModel {

    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
