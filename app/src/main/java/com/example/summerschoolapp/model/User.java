package com.example.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("oib")
    private String oib;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public User(String oib, String email, String password) {
        this.oib = oib;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
