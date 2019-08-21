package com.example.summerschoolapp.model.userslist;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersListInnerResponse extends BaseModel {

    @SerializedName("user")
    @Expose
    public ArrayList<User> user;
}
