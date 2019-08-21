package com.example.summerschoolapp.model.signup;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.AuthInnerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSignup extends BaseModel {
    @SerializedName("data")
    @Expose
    public AuthInnerResponse data;
}
