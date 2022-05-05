package com.example.mymusic.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mymusic.Entity.LocalMusic;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int currnetPlayPosition = intent.getIntExtra("currnetPlayPosition",0);
        Toast.makeText(context,"currnetPlayPosition="+currnetPlayPosition,Toast.LENGTH_LONG).show();
    }
}
