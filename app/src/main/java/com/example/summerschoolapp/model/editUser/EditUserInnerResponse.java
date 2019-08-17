package com.example.summerschoolapp.model.editUser;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditUserInnerResponse extends BaseModel {
    @SerializedName("user")
    @Expose
    public EditUser editUser;
}
