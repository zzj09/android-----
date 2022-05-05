package com.example.mymusic.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.Entity.OnlineMusic;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private LocalMusic localMusic;
    private OnlineMusic onlineMusic;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Mybinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public class Mybinder extends Binder{
        private String name = "未知";
        private String author = "未知";
        private Bitmap bm = null;
        public void setMusicInfo(LocalMusic lMusic) {
            System.out.println("------------Mybinder---------------");
            mediaPlayer.stop();
            mediaPlayer.reset();
            if(lMusic != null){
                localMusic = lMusic;
                name = localMusic.getMusic();
                author = localMusic.getAuthor();
                bm = localMusic.getBitmap();
                try {
                    mediaPlayer.setDataSource(localMusic.getPath());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public String getName(){
            return name;
        }

        public String getAuthor(){
            return author;
        }

        public Bitmap getBm(){
            return bm;
        }

        public void playState(){
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }

        public boolean isplay(){return mediaPlayer.isPlaying();}

        public int getDuartion(){return mediaPlayer.getDuration();}

        public int getCurrenPostion(){return mediaPlayer.getCurrentPosition();}

        public void seeKTo(int time){mediaPlayer.seekTo(time);}

    }
}
