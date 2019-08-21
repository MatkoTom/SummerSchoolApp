package com.example.summerschoolapp.model.userslist;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUsersList extends BaseModel {

    @SerializedName("data")
    @Expose
    public UsersListInnerResponse data;
}
