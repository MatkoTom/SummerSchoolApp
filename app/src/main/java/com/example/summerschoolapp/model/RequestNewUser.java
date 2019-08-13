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
}
