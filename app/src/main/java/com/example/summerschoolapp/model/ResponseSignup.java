package com.example.summerschoolapp.model;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSignup extends BaseModel {
    @SerializedName("data")
    @Expose
    public SignupInnerResponse data;
}
