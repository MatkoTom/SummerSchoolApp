package com.example.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("oib")
    private String oib;

    @SerializedName("firstName")
    private String name;

    @SerializedName("token")
    private String token;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("personsRoleId")
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
