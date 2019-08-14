package com.example.summerschoolapp.model.login;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.signup.SignupInnerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin extends BaseModel {
    @SerializedName("data")
    @Expose
    public SignupInnerResponse data;
}
