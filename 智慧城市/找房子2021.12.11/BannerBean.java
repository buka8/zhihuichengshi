package com.example.smartcity;

public class BannerBean {
    private String img;
    private String title;
    private int id;

    public BannerBean(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public BannerBean(String img, int id) {
        this.img = img;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
