package com.example.summerschoolapp.model.signup;

import com.google.gson.annotations.SerializedName;

public class RequestSignup {

    @SerializedName("oib")
    public String oib;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;
}
