package com.example.mymusic.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Activity.Play;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;

import java.util.List;

public class MyAdapter_localMusic extends RecyclerView.Adapter<MyAdapter_localMusic.ViewHolder> {
    public static final String TAG="localMusic";
    private Context context;
    private int resource;
    private List<LocalMusic> myData;
    public OnItemClickListener listener;

    public MyAdapter_localMusic(Context context, int resource, List<LocalMusic> myData) {
        this.context = context;
        this.resource = resource;
        this.myData = myData;
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
        LocalMusic localMusic = myData.get(position);
        holder.imageView.setImageBitmap(localMusic.getBitmap());
        holder.textView1.setText(localMusic.getMusic());
        holder.textView2.setText(localMusic.getAuthor());
        holder.textView3.setText(localMusic.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
                Log.d(TAG,localMusic.getMusic());
                Log.d(TAG,localMusic.getAuthor());
            }
        });
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView16);
            textView1 = itemView.findViewById(R.id.textView10);
            textView2 = itemView.findViewById(R.id.textView11);
            textView3 = itemView.findViewById(R.id.textView12);
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }
}