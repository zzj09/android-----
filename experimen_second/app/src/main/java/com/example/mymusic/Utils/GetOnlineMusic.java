package com.example.mymusic.Utils;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Handler;

public class GetOnlineMusic extends Thread{
    private long musicID;
    private URL url = null;
    private Handler handler;

    public GetOnlineMusic(long musicID,Handler handler){
        this.musicID = musicID;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        String msg = "";
        try {
            url = new URL("http://music.eleuu.com/song/url?br=128000&id="+musicID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String result = "";
            while((result = reader.readLine()) != null){
                msg += result;
            }
            Message message = new Message();
            message.obj = msg;
            message.what = 2;
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
