package com.example.mymusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymusic.Adapter.MyAdapter_onlineList;
import com.example.mymusic.Entity.OnlineMusicPackage;
import com.example.mymusic.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnlineList extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageView1;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinelist);

        //接收信息
        Bundle bundle=getIntent().getExtras();
        OnlineMusicPackage OnlineMusicPackage= (OnlineMusicPackage) bundle.getSerializable("OnlineMusicPackage");

        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        MyAdapter_onlineList myAdapter_onlineList=new MyAdapter_onlineList(OnlineList.this,OnlineMusicPackage.getMusics(),R.layout.list_view);
        recyclerView.setAdapter(myAdapter_onlineList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        textView=findViewById(R.id.onlineTopText);
        textView1=findViewById(R.id.onlineName);
        textView2=findViewById(R.id.updateTime);
        textView3=findViewById(R.id.description);
        imageView=findViewById(R.id.onlineReturn);
        imageView1=findViewById(R.id.onlineAlbumImage);
        textView.setText(OnlineMusicPackage.getName());
        textView1.setText(OnlineMusicPackage.getName());
        Date d=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        textView2.setText("最近更新:"+sdf.format(d));
        textView3.setText(OnlineMusicPackage.getDescription());
        Glide.with(this).load(OnlineMusicPackage.getImgUrl()).into(imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Item垂直排列的分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(OnlineList.this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.rgb(253,4,4));
    }
}
