package com.example.atest;

public class CitysBean {
    private String name;
    private int imageId;
    public CitysBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
}
