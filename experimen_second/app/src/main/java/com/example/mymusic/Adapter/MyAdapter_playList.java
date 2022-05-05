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

import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;

import java.util.List;


public class MyAdapter_playList extends RecyclerView.Adapter<MyAdapter_playList.ViewHolder> {
    private Context context;
    private int resource;
    List<LocalMusic> myData;
    public OnItemClickListener listener;

    public MyAdapter_playList(Context context, int resource, List<LocalMusic> myData) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        LocalMusic localMusic = myData.get(position);
        holder.album.setImageBitmap(localMusic.getBitmap());
        holder.musicName.setText(localMusic.getMusic());
        holder.musicAuthor.setText(localMusic.getAuthor());

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
        return myData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView album;
        ImageView imageView2;
        TextView musicName;
        TextView musicAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.listView_picture);
            imageView2 = itemView.findViewById(R.id.listView_smallpicture);
            musicName = itemView.findViewById(R.id.listView_music);
            musicAuthor = itemView.findViewById(R.id.listView_author);
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }
}