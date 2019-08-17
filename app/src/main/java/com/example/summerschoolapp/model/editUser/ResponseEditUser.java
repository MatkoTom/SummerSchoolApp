package com.example.summerschoolapp.model.editUser;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.newuser.NewUserInnerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEditUser extends BaseModel {
    @SerializedName("data")
    @Expose
    public NewUserInnerResponse data;
}
