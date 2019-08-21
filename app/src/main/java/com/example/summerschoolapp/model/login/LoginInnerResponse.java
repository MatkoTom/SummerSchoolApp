package com.example.summerschoolapp.model.login;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInnerResponse extends BaseModel {
    @SerializedName("user")
    @Expose
    public User user;
}
