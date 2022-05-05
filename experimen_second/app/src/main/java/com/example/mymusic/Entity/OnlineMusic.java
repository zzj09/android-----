package com.example.mymusic.Entity;

import android.graphics.Bitmap;

import java.io.Serializable;

public class OnlineMusic implements Serializable {
    private Long id;
    private String name;
    private String author;
    private String imgUrl;
    private Bitmap imgBitmap;
    private String song;  //歌词
    private String path;

    @Override
    public String toString() {
        return "OnlineMusic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgBitmap=" + imgBitmap +
                ", song='" + song + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }
}
