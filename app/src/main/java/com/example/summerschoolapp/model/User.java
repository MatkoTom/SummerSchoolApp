package com.example.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("oib")
    private int oib;

    @SerializedName("title")
    private String email;

    @SerializedName("text")
    private String password;

    public User(int oib, String email, String password) {
        this.oib = oib;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getOib() {
        return oib;
    }

    public void setOib(int oib) {
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
