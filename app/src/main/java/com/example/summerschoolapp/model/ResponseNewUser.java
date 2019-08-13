package com.example.summerschoolapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNewUser {
    @SerializedName("data")
    @Expose
    public SignupInnerResponse data;
}
