package com.example.summerschoolapp.model.login;

import com.google.gson.annotations.SerializedName;

public class RequestLogin {

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;
}
