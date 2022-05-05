package com.example.mymusic.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymusic.Adapter.MyAdapter_onlineMusic;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.Entity.OnlineMusic;
import com.example.mymusic.Entity.OnlineMusicPackage;
import com.example.mymusic.R;
import com.example.mymusic.Utils.GetOnlineJSON;
import com.example.mymusic.Utils.ParseOnlineJSON;

import java.util.ArrayList;
import java.util.List;


public class OnlineMusicFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private String mtextString;
    View view;

    public static OnlineMusicFragment newInstance() {
        OnlineMusicFragment fragment = new OnlineMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mtextString = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_online, container, false);
        List<OnlineMusicPackage> onlineMusicPackages = new ArrayList<>();
        RecyclerView onlineMusicRecyclerView=view.findViewById(R.id.recyclerView2);

        MyAdapter_onlineMusic myAdapter_onlineMusic = new MyAdapter_onlineMusic(getContext(),R.layout.online_view,onlineMusicPackages);
        onlineMusicRecyclerView.setAdapter(myAdapter_onlineMusic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        onlineMusicRecyclerView.setLayoutManager(linearLayoutManager);

        myAdapter_onlineMusic.setOnItemClickListener(new MyAdapter_onlineMusic.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                callbacks.onMusicSelected(onlineMusicPackages.get(position));
            }
            @Override
            public void onItemClick(int position,int position2) {
                callbacks.onMusicSelected(onlineMusicPackages.get(position).getMusics().get(position2));
            }
        });


        //Item垂直排列的分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        onlineMusicRecyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    private Callbacks callbacks;
    public interface Callbacks{
        void onMusicSelected(OnlineMusic music);
        void onMusicSelected(OnlineMusicPackage onlineMusicPackage);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context ;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}
