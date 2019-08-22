package com.example.summerschoolapp.model.newRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.editUser.EditUserInnerResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNewRequest extends BaseModel {

    @SerializedName("response")
    public String ok;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "ResponseNewRequest{" +
                "ok='" + ok + '\'' +
                '}';
    }
}
