package com.example.mymusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Adapter.MyAdapter_playList;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;
import com.example.mymusic.Utils.LocalMusicUtils;

import java.util.List;

public class PlayList extends AppCompatActivity {
    private int currnetPlayPosition = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        currnetPlayPosition = bundle.getInt("position");
        String musicName = bundle.getString("musicName");
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);;

        if (!musicName.equals("未播放") && !musicName.equals("未知")){
            LocalMusicUtils music = new LocalMusicUtils();
            List<LocalMusic> myData= music.getLocalMusic(PlayList.this);
            MyAdapter_playList arrayAdapter=new MyAdapter_playList(PlayList.this,R.layout.list_view,myData);
            arrayAdapter.setOnItemClickListener(new MyAdapter_playList.OnItemClickListener(){
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(PlayList.this,"点击了\""+myData.get(position).getMusic()+"\"，歌手是"+ myData.get(position).getAuthor(),Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(PlayList.this, Play.class);
                    Bundle bundle = new Bundle();
//                bundle.putInt("musicType",1);
                    bundle.putInt("currnetPlayPosition",position);
                    bundle.putParcelable("localMusic",myData.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(arrayAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PlayList.this);
            recyclerView.setLayoutManager(linearLayoutManager);
        }


        //Item垂直排列的分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(PlayList.this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.rgb(253,4,4));
    }
}
