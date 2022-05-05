package com.example.mymusic.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymusic.Adapter.MyFragmentAdapter;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.Entity.OnlineMusic;
import com.example.mymusic.Entity.OnlineMusicPackage;
import com.example.mymusic.R;
import com.example.mymusic.Fragment.LocalMusicFragment;
import com.example.mymusic.Fragment.OnlineMusicFragment;
import com.example.mymusic.Utils.MyReceiver;
import com.example.mymusic.services.MusicService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocalMusicFragment.Callbacks,OnlineMusicFragment.Callbacks{
    private ViewPager2 viewPager2;
    public static final String TAG="name";
    private ImageView bar_picture=null;
    private TextView bar_name=null;
    private TextView bar_author=null;
    private LocalMusic localMusic;
    private List<LocalMusic> localMusicList;
    private ImageView music_start;
    private ImageView nextMusic;
    private Intent serviceIntent;
    private int currnetPlayPosition = -1;
    private MusicService.Mybinder mybinder;
    private MyReceiver myReceiver;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            switch (message.what){
                case 1:
                    bar_name.setText(mybinder.getName());
                    bar_author.setText(mybinder.getAuthor());
                    bar_picture.setImageBitmap(mybinder.getBm());
                    isplay();
                    break;
            }
        }
    };
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mybinder = (MusicService.Mybinder) service;
            startService(serviceIntent);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getData() != null && result.getResultCode() == Activity.RESULT_CANCELED){
                localMusic = result.getData().getExtras().getParcelable("localMusic1");
                Log.d("result",localMusic.toString());
                bar_picture.setImageBitmap(localMusic.getBitmap());
                bar_name.setText(localMusic.getMusic());
                bar_author.setText(localMusic.getAuthor());

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(MainActivity.this, MusicService.class);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);

        //接收广播，并重写方法赋值
        myReceiver = new MyReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                currnetPlayPosition = intent.getIntExtra("currnetPlayPosition",0);
            }
        };
        IntentFilter filter = new IntentFilter("com.example.mymusic.currnetPlayPosition");
        registerReceiver(myReceiver,filter);

        TextView local = findViewById(R.id.textView);
        TextView online = findViewById(R.id.textView3);
        bar_picture = findViewById(R.id.bar_picture);
        bar_name = findViewById(R.id.musicName);
        bar_author = findViewById(R.id.musicAuthor);
        nextMusic = findViewById(R.id.Main_nextMusic);


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(0);
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(1);
            }
        });
        initPager();

        //跳转播放界面
        bar_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Play.class);
                Bundle bundle = new Bundle();
                bundle.putInt("currnetPlayPosition",currnetPlayPosition);
                bundle.putParcelable("localMusic",localMusic);
                intent.putExtras(bundle);
                activityResultLauncher.launch(intent);
//                startActivity(intent);
            }
        });

        //播放/暂停按钮
        music_start = findViewById(R.id.music_start);
        isplay();
        music_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mybinder.playState();
                isplay();
            }
        });

        //跳转播放列表
        ImageView imageView1=findViewById(R.id.imageView14);
        imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,PlayList.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",currnetPlayPosition);
                bundle.putString("musicName",bar_name.getText().toString());
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        //下一首
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currnetPlayPosition == localMusicList.size()-1) {
                    Toast.makeText(MainActivity.this,"已经是最后一首了，没有下一曲！",Toast.LENGTH_SHORT).show();
                    return;
                }
                currnetPlayPosition = currnetPlayPosition+1;
                localMusic = localMusicList.get(currnetPlayPosition);
                if(mybinder != null){
                    bar_picture.setImageBitmap(localMusic.getBitmap());
                    mybinder.setMusicInfo(localMusic);
                    handler.sendEmptyMessage(1);
                }
            }
        });

        Window window = this.getWindow();
        window.setStatusBarColor(Color.rgb(253,4,4));

    }

    private void initPager() {
        viewPager2=findViewById(R.id.viewpager);
        ArrayList<Fragment> arrayList=new ArrayList<>();
        arrayList.add(LocalMusicFragment.newInstance());
        arrayList.add(OnlineMusicFragment.newInstance());
        MyFragmentAdapter myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager(),getLifecycle(),arrayList);
        viewPager2.setAdapter(myFragmentAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {
        TextView bendi=findViewById(R.id.textView);
        TextView zaixian=findViewById(R.id.textView3);
        switch (position){
            case 0:
                viewPager2.setCurrentItem(position);
                zaixian.setAlpha(0.5F);
                bendi.setAlpha(1F);
                break;
            case 1:
                viewPager2.setCurrentItem(position);
                zaixian.setAlpha(1F);
                bendi.setAlpha(0.5F);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG,"授权成功");
                    changeTab(0);
                    initPager();
                }else{
                    //用户拒绝授权则出现提示
                    Toast.makeText(MainActivity.this,"未授权读取文件",Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    public void onMusicSelected(List<LocalMusic> music, int position) {
        localMusicList = music;
        localMusic = music.get(position);
        bar_picture.setImageBitmap(localMusic.getBitmap());
        bar_name.setText(localMusic.getMusic());
        bar_author.setText(localMusic.getAuthor());
        currnetPlayPosition = position;
    }

    @Override
    public void onMusicSelected(OnlineMusic music) {
        Glide.with(MainActivity.this).load(music.getImgUrl()).into(bar_picture);
        bar_name.setText(music.getName());
        bar_author.setText(music.getAuthor());
    }

    @Override
    public void onMusicSelected(OnlineMusicPackage onlineMusicPackage) {

    }

    public void isplay(){
        if(mybinder != null){
            if(mybinder.isplay()){
                music_start.setImageResource(R.drawable.ic_play_bar_btn_pause);
            }
            else{
                music_start.setImageResource(R.drawable.ic_play_bar_btn_play);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mybinder != null){
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unregisterReceiver(myReceiver);
    }

}