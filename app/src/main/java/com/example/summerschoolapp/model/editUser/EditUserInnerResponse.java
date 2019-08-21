package com.example.summerschoolapp.model.editUser;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO @Matko
// it is best to name variable and serialized name the same
// what is this used for, looks like it is not used
public class EditUserInnerResponse extends BaseModel {
    @SerializedName("user")
    @Expose
    public String editUserResponse;
}
