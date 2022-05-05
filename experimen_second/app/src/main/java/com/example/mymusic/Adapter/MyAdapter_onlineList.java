package com.example.mymusic.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymusic.Entity.OnlineMusic;
import com.example.mymusic.R;

import java.util.List;

public class MyAdapter_onlineList extends RecyclerView.Adapter<MyAdapter_onlineList.ViewHolder> {
    public MyAdapter_playList.OnItemClickListener listener;
    private Context context;
    private List<OnlineMusic> onlineMusics;
    private int resource;

    public MyAdapter_onlineList(Context context, List<OnlineMusic> onlineMusics, int resource) {
        this.context = context;
        this.onlineMusics = onlineMusics;
        this.resource = resource;
    }


    public void setOnItemClickListener(MyAdapter_playList.OnItemClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyAdapter_onlineList.ViewHolder viewHolder;
        View view;
        view = LayoutInflater.from(context).inflate(resource,parent,false);
        viewHolder = new MyAdapter_onlineList.ViewHolder(view);
        return viewHolder;
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        OnlineMusic onlineMusic=onlineMusics.get(position);
        holder.textView1.setText(onlineMusic.getName());
        holder.textView2.setText(onlineMusic.getAuthor());
        try{
            Glide.with(context).load(onlineMusic.getImgUrl()).into(holder.imageView1);
        }catch(Exception e){
            e.printStackTrace();
            Glide.with(context).load("null").into(holder.imageView1);
        }
        if(listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return onlineMusics.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageView1;
        ImageView imageView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.listView_picture);
            imageView2 = itemView.findViewById(R.id.listView_smallpicture);
            textView1 = itemView.findViewById(R.id.listView_music);
            textView2 = itemView.findViewById(R.id.listView_author);
        }

    }
    public interface OnItemClickListener{
        public void onItemClick(int position);
    }
}
