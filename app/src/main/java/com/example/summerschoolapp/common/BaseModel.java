package com.example.summerschoolapp.common;

import com.example.summerschoolapp.model.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("error")
    @Expose
    public Error error;
}
