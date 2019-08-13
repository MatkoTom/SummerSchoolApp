package com.example.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

public class RequestNewUser {

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

    //TODO remove this line, only for testing purposes, will pass JWT of stored user
    @SerializedName("token")
    public String token;
}
