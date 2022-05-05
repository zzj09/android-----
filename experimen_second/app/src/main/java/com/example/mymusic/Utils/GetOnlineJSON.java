package com.example.mymusic.Utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetOnlineJSON extends Thread{
    private static final String TAG = "name";
    private String idx;
    private URL url=null;
    private Handler handler;
    private int type;

    public GetOnlineJSON(String idx,Handler handler,int type){
        this.idx = idx;
        this.handler = handler;
        this.type = type;
    }

    @Override
    public void run() {
        super.run();
        String msg = "";
        try {
            url = new URL("http://music.eleuu.com/top/list?idx="+idx);
            Log.d(TAG,"url: "+url);
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
            message.what = this.type;
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
