package com.example.summerschoolapp.model.newuser;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewUserInnerResponse extends BaseModel {
    @SerializedName("user")
    @Expose
    public NewUser newUser;
}
