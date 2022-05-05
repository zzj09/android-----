package com.example.mymusic.Utils;

import com.example.mymusic.Entity.OnlineMusic;
import com.example.mymusic.Entity.OnlineMusicPackage;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseOnlineJSON {
    private JSONObject jsonObject;
    public ParseOnlineJSON(String data) {
        try {
            this.jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public OnlineMusicPackage getOnlineMusiclist() {

        try {
            JSONObject playList = jsonObject.getJSONObject("playlist");
            long id = playList.getLong("id");
            String name = playList.getString("name");
            String coverImgUrl = playList.getString("coverImgUrl");
            String description = playList.getString("description");
            JSONArray tracks = playList.getJSONArray("tracks");

            JSONObject track;

            List<OnlineMusic> musicList = new ArrayList<>();
            for(int i = 0; i < tracks.length(); i++) {
                OnlineMusic music = new OnlineMusic();
                track = tracks.getJSONObject(i);
                String picture = track.getJSONObject("al").getString("picUrl");
                String musicname = track.getString("name");
                long musicid = track.getLong("id");
                String singer = "";
                JSONArray trackArr = track.getJSONArray("ar");
                for (int j = 0; j < trackArr.length(); j++) {
                    singer += trackArr.getJSONObject(j).getString("name") + " ";
                }

                music.setId(musicid);
                music.setName(musicname);
                music.setImgUrl(picture);
                music.setAuthor(singer);
                musicList.add(music);
            }
            OnlineMusicPackage onlineMusicPackage = new OnlineMusicPackage(id,name,coverImgUrl,description,musicList);
            return  onlineMusicPackage;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMusicUrl(){
        try {
            JSONArray data = jsonObject.getJSONArray("data");
            JSONObject musicInfo = data.getJSONObject(0);
            String path = musicInfo.getString("url");

            return  path;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
