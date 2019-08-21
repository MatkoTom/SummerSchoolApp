package com.example.summerschoolapp.model.login;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.signup.SignupInnerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin extends BaseModel {
    // TODO @Matko
    // if singnup and login are using same response object it is better to create a new universal one
    // for example AuthInnerResponse
    @SerializedName("data")
    @Expose
    public SignupInnerResponse data;
}
