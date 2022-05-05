package com.example.mymusic.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mymusic.Adapter.MyFragmentAdapter;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;
import com.example.mymusic.Fragment.SongFragment;
import com.example.mymusic.Fragment.AlbumFragment;
import com.example.mymusic.Utils.LocalMusicUtils;
import com.example.mymusic.services.MusicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Play extends AppCompatActivity{
    private static final String TAG = "Play.class";
    private MusicService.Mybinder mybinder;
    private Intent serviceIntent;
    public LocalMusic localMusic;
    private List<LocalMusic> myData;
    private SeekBar seekBar;
    private TextView nowTime;
    private TextView allTime;
    private TextView musicName;
    private TextView musicAuthor;
    private ImageView m_start;
    private ImageView album_picture;
    private ImageView nextMusic;  //下一首
    private ImageView lastMusic;  //上一首
    private int currnetPlayPosition = -1;

    private Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    System.out.println("------------localmusicplayer---------------");
                    musicName.setText(localMusic.getMusic());
                    musicAuthor.setText(localMusic.getAuthor());
                    album_picture.setImageBitmap(localMusic.getBitmap());
                    m_start.setImageResource(R.drawable.ic_play_btn_pause);
                    if(mybinder != null){
                        mybinder.setMusicInfo(localMusic);
                        updatePlayText();
                    }
                    break;
                case 2:
                    if(mybinder != null){
                        seekBar.setProgress(mybinder.getCurrenPostion());
                        nowTime.setText(getTime(mybinder.getCurrenPostion()));
                        seekBar.setMax(mybinder.getDuartion());
                        allTime.setText(getTime(mybinder.getDuartion()));
                        handler.sendEmptyMessageDelayed(2,200);
                    };
                    break;
                case 3:
                    ((TextView) findViewById(R.id.musicName)).setText(mybinder.getName());
                    ((TextView) findViewById(R.id.musicAuthor)).setText(mybinder.getAuthor());
                    isplay();
            }
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mybinder = (MusicService.Mybinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        //暂时不用歌词
//        AlbumFragment fragment1 = new AlbumFragment();
//        SongFragment fragment2 = new SongFragment();
//        List<Fragment> fragmentList = new ArrayList<Fragment>();
//        fragmentList.add(fragment1);
//        fragmentList.add(fragment2);

        LocalMusicUtils musicUtils = new LocalMusicUtils();
        myData = musicUtils.getLocalMusic(Play.this);

        Bundle bundle = getIntent().getExtras();
        currnetPlayPosition = bundle.getInt("currnetPlayPosition");
        localMusic = new LocalMusic();
        localMusic = myData.get(currnetPlayPosition);


        serviceIntent = new Intent(Play.this,MusicService.class);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);

        musicName = findViewById(R.id.textView0);
        musicAuthor = findViewById(R.id.textView9);
        album_picture = findViewById(R.id.play_album_picture);

        musicName.setText(localMusic.getMusic());
        musicAuthor.setText(localMusic.getAuthor());
        album_picture.setImageBitmap(localMusic.getBitmap());
        handler.sendEmptyMessage(2);

        nowTime = findViewById(R.id.nowTime);
        allTime = findViewById(R.id.allTime);
        seekBar = findViewById(R.id.seekBar);
        m_start = findViewById(R.id.imageView4);
        nextMusic = findViewById(R.id.nextMusic);
        lastMusic = findViewById(R.id.lastMusic);

        isplay();

        //回传信息到MainActivity
        Intent data = new Intent();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("localMusic1",localMusic);
        data.putExtras(bundle1);
        setResult(Activity.RESULT_CANCELED,data);


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.mymusic.currnetPlayPosition");
                intent.putExtra("currnetPlayPosition",currnetPlayPosition);
                sendBroadcast(intent);
                finish();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //判断是否认为操作
                if (fromUser){
                    mybinder.seeKTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //播放/暂停键
        m_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybinder.playState();
                isplay();
            }
        });
        //下一首
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currnetPlayPosition == myData.size()-1) {
                    Toast.makeText(Play.this,"已经是最后一首了，没有下一曲！",Toast.LENGTH_SHORT).show();
                    return;
                }
                currnetPlayPosition = currnetPlayPosition+1;
                localMusic = myData.get(currnetPlayPosition);
                handler.sendEmptyMessage(1);
//                Intent intent = new Intent("com.example.mymusic.currnetPlayPosition");
//                intent.putExtra("currnetPlayPosition",currnetPlayPosition);
//                sendBroadcast(intent);
            }
        });
        //上一首
        lastMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currnetPlayPosition == 0) {
                    Toast.makeText(Play.this,"已经是第一首了，没有上一曲！",Toast.LENGTH_SHORT).show();
                    return;
                }
                currnetPlayPosition = currnetPlayPosition-1;                    //序号索引-1
                localMusic = myData.get(currnetPlayPosition);  //从数组中获取上一首信息
                handler.sendEmptyMessage(1);
//                Intent intent = new Intent("com.example.mymusic.currnetPlayPosition");
//                intent.putExtra("currnetPlayPosition",currnetPlayPosition);
//                sendBroadcast(intent);
            }
        });
        handler.sendEmptyMessage(1);

        //暂时不用
//        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
//        MyFragmentAdapter adapter = new MyFragmentAdapter(this,fragmentList);
//        viewPager2.setAdapter(adapter);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.rgb(126,109,109));
    }

    //重写硬件返回键的功能，发生广播
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent("com.example.mymusic.currnetPlayPosition");
        intent.putExtra("currnetPlayPosition",currnetPlayPosition);
        sendBroadcast(intent);
    }

    public void updatePlayText() {
        try {
            allTime.setText(getTime(mybinder.getDuartion()));
            seekBar.setMax(mybinder.getDuartion());
            handler.sendEmptyMessage(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //进入到界面后开始更新进度条
        if (mybinder != null){
            handler.sendEmptyMessage(3);
            handler.sendEmptyMessageDelayed(2,500);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    public String getTime(int musictime){
        String timeStr = "0:00";
        if(mybinder != null){
            int time = musictime;
            int min = (time % (1000 * 60 * 60)) / (1000 * 60);
            int sec = (time % (1000 * 60)) / 1000;
            if (sec < 10){
                timeStr = min + ":0" + sec;
            }else{
                timeStr = min + ":" + sec;
            }
        }
        return timeStr;
    }

    public void isplay(){
        if(mybinder != null){
            if(mybinder.isplay()){
                m_start.setImageResource(R.drawable.ic_play_btn_pause);
            }else{
                m_start.setImageResource(R.drawable.ic_play_btn_play);
            }
        }
    }
}
