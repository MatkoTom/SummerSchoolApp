package com.example.summerschoolapp.model.requestsList;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.userslist.UsersListInnerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestList extends BaseModel {

    @SerializedName("data")
    @Expose
    public RequestListInnerResponse data;
}
