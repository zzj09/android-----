package com.example.mymusic.Utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicUtils {
    public static final String TAG="name";

    public List<LocalMusic> getLocalMusic(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        //读取sd卡文件
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //读取系统文件
//        Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        @SuppressLint("Recycle")
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        List<LocalMusic> myData=new ArrayList<>();
        while(cursor.moveToNext()){
            @SuppressLint("Range") String music = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            @SuppressLint("Range") long imgID = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            @SuppressLint("Range") int time = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            @SuppressLint("Range") int musicID = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            ArtworkUtils artworkUtils = new ArtworkUtils();
            Bitmap bm=artworkUtils.getArtwork(context,musicID,imgID,true);
            String time2;
            if (bm == null && author != null){
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_cover);
            }
            if (bm!=null && !author.equals("<unknown>")){
                int time1=time/1000;
                int time_min = time1/60;
                int time_second = time1%60;
                if (time_second<10){
                    time2 = time_min+":0"+time_second;
                }else{
                    time2 = time_min+":"+time_second;
                }

                ChangeBitmapSize bmSize = new ChangeBitmapSize();

                LocalMusic localMusic = new LocalMusic();
                localMusic.setBitmap(bmSize.changeBitmapSize(bm,300,300));
                localMusic.setPath(path);
                localMusic.setMusicID(musicID);
                localMusic.setMusic(music);
                localMusic.setAuthor(author);
                localMusic.setMusicTime(time);
                localMusic.setTime(time2);
                myData.add(localMusic);

                Log.d(TAG,"歌曲名:"+music);
                Log.d(TAG,"歌手名:"+author);
                Log.d(TAG,"bm:"+bm);
                Log.d(TAG,"time:"+time2);
                Log.d(TAG,"音乐时长:"+time);
            }
        }
        return myData;
    }
}
