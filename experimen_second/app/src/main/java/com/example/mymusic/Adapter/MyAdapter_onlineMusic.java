package com.example.mymusic.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymusic.Activity.MainActivity;
import com.example.mymusic.Activity.OnlineList;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.Entity.OnlineMusic;
import com.example.mymusic.Entity.OnlineMusicPackage;
import com.example.mymusic.Fragment.LocalMusicFragment;
import com.example.mymusic.Fragment.OnlineMusicFragment;
import com.example.mymusic.R;
import com.example.mymusic.Utils.GetOnlineJSON;
import com.example.mymusic.Utils.ParseOnlineJSON;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter_onlineMusic extends RecyclerView.Adapter<MyAdapter_onlineMusic.ViewHolder> {
    private static final String TAG = "onlineMusic";
    private Context context;
    private int resource;
    private List<OnlineMusicPackage> onlineMusicPackageList;
    public OnItemClickListener listener;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ParseOnlineJSON parseOnlineJSON = new ParseOnlineJSON(msg.obj.toString());
            switch (msg.what){
                case 1:
                    //获取榜单列表
                    onlineMusicPackageList.add(parseOnlineJSON.getOnlineMusiclist());
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    public MyAdapter_onlineMusic(Context context, int resource, List<OnlineMusicPackage> onlineMusicPackageList) {
        this.context = context;
        this.resource = resource;
        this.onlineMusicPackageList = onlineMusicPackageList;
        for (int idx = 0;idx < 11;idx++){
            new GetOnlineJSON(idx+"",handler,1).start();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        View view;
        view = LayoutInflater.from(context).inflate(resource,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnlineMusicPackage onlineMusicPackage = onlineMusicPackageList.get(position);
        List<OnlineMusic> musicList = onlineMusicPackage.getMusics();

        try{
            Glide.with(context).load(onlineMusicPackage.getImgUrl()).into(holder.album_picture);
        }catch(Exception e){
            e.printStackTrace();
            Glide.with(context).load("null").into(holder.album_picture);
        }
        try{
            holder.music1.setText("1 "+ musicList.get(0).getName() + " - " + musicList.get(0).getAuthor());
        }catch(Exception e){
            e.printStackTrace();
            holder.music1.setText("无");
        }

        try{
            holder.music2.setText("2 "+ musicList.get(1).getName() + " - " + musicList.get(1).getAuthor());
        }catch(Exception e){
            e.printStackTrace();
            holder.music2.setText("无");
        }

        try{
            holder.music3.setText("3 "+ musicList.get(2).getName() + " - " + musicList.get(2).getAuthor());
        }catch(Exception e){
            e.printStackTrace();
            holder.music3.setText("无");
        }

        if(listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
            holder.music1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"你点击了第一首歌");
                    Log.d(TAG,musicList.get(position).toString());
                    listener.onItemClick(position,0);
                }
            });
            holder.music2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"你点击了第二首歌");
                    listener.onItemClick(position,1);
                }
            });
            holder.music3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"你点击了第三首歌");
                    listener.onItemClick(position,2);
                }
            });
            holder.album_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,OnlineList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("OnlineMusicPackage",onlineMusicPackage);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return onlineMusicPackageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView album_picture;
        TextView music1;
        TextView music2;
        TextView music3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            album_picture = itemView.findViewById(R.id.album_picture);
            music1 = itemView.findViewById(R.id.music1);
            music2 = itemView.findViewById(R.id.music2);
            music3 = itemView.findViewById(R.id.music3);
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
        public void onItemClick(int position,int position2);
    }
}