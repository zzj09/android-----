package com.example.mymusic.Entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class OnlineMusicPackage implements Serializable {
    private Long id;
    private String name;
    private String imgUrl;
    private Bitmap imgBitmap;
    private String description;
    private List<OnlineMusic> musics;

    public OnlineMusicPackage(Long id, String name, String imgUrl, String description, List<OnlineMusic> musics) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
        this.musics = musics;
    }


    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OnlineMusicPackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgBitmap=" + imgBitmap +
                ", description='" + description + '\'' +
                ", musics=" + musics +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OnlineMusic> getMusics() {
        return musics;
    }

    public void setMusics(List<OnlineMusic> musics) {
        this.musics = musics;
    }
}
