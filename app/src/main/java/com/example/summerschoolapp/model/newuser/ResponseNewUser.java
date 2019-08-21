package com.example.summerschoolapp.model.newuser;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNewUser extends BaseModel {
    @SerializedName("data")
    @Expose
    public NewUserInnerResponse data;
}
