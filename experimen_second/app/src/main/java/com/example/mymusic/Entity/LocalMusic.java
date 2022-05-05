package com.example.mymusic.Entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class LocalMusic implements Parcelable {
    private String author;
    private String music;
    private long imgID;
    private long musicID;
    private String path;
    private Bitmap bitmap;
    private int musicTime;
    private String Time;

    public LocalMusic() {
    }

    protected LocalMusic(Parcel in) {
        author = in.readString();
        music = in.readString();
        path = in.readString();
        imgID = in.readLong();
        musicID = in.readLong();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        musicTime = in.readInt();
        Time = in.readString();
    }

    public static final Creator<LocalMusic> CREATOR = new Creator<LocalMusic>() {
        @Override
        public LocalMusic createFromParcel(Parcel in) {
            return new LocalMusic(in);
        }

        @Override
        public LocalMusic[] newArray(int size) {
            return new LocalMusic[size];
        }
    };

    @Override
    public String toString() {
        return "LocalMusic{" +
                "author='" + author + '\'' +
                ", music='" + music + '\'' +
                ", imgID=" + imgID +
                ", musicID=" + musicID +
                ", path='" + path + '\'' +
                ", bitmap=" + bitmap +
                ", musicTime=" + musicTime +
                ", Time='" + Time + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public long getImgID() {
        return imgID;
    }

    public void setImgID(long imgID) {
        this.imgID = imgID;
    }

    public long getMusicID() { return musicID; }

    public void setMusicID(long musicID) { this.musicID = musicID; }

    public int getMusicTime() { return musicTime; }

    public void setMusicTime(int musicTime) { this.musicTime = musicTime; }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(music);
        parcel.writeString(path);
        parcel.writeLong(imgID);
        parcel.writeLong(musicID);
        parcel.writeParcelable(bitmap, i);
        parcel.writeInt(musicTime);
        parcel.writeString(Time);
    }
}
