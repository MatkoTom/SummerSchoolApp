package com.example.summerschoolapp.model;

import com.example.summerschoolapp.common.BaseModel;

public class Photo extends BaseModel {

    private String images;

    public Photo(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
