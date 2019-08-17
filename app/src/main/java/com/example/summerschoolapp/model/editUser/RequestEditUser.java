package com.example.summerschoolapp.model.editUser;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class RequestEditUser extends BaseModel {

    @SerializedName("ID")
    public String id;

    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("oib")
    public String oib;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("photo")
    public File photo;
}
