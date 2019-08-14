package com.example.summerschoolapp.model.signup;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupInnerResponse extends BaseModel {
    @SerializedName("user")
    @Expose
    public User user;
}
