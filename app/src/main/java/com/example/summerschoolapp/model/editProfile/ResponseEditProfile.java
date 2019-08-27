package com.example.summerschoolapp.model.editProfile;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEditProfile extends BaseModel {
    @SerializedName("data")
    @Expose
    public EditProfileInnerResponse data;
}
