package com.example.mymusic.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mymusic.Activity.Play;
import com.example.mymusic.Adapter.MyAdapter_localMusic;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;
import com.example.mymusic.Utils.LocalMusicUtils;

import java.util.List;


public class LocalMusicFragment extends Fragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
    private String mtextString;
    private int currnetPlayPosition = -1;
    View view;

    public static LocalMusicFragment newInstance() {
        LocalMusicFragment fragment = new LocalMusicFragment();
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
        view=inflater.inflate(R.layout.fragment_local, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView1);

        LocalMusicUtils music = new LocalMusicUtils();
        List<LocalMusic> myData=music.getLocalMusic(this.getContext());
        MyAdapter_localMusic arrayAdapter=new MyAdapter_localMusic(getContext(),R.layout.local_view,myData);

        arrayAdapter.setOnItemClickListener(new MyAdapter_localMusic.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                callbacks.onMusicSelected(myData,position);
                currnetPlayPosition = position;
                Intent intent = new Intent(getContext(), Play.class);
                Bundle bundle = new Bundle();
//                bundle.putInt("musicType",1);
                bundle.putInt("currnetPlayPosition",currnetPlayPosition);
                bundle.putParcelable("localMusic",myData.get(position));
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
        recyclerView.setAdapter(arrayAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        //Item垂直排列的分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    private Callbacks callbacks;
    public interface Callbacks{
        void onMusicSelected(List<LocalMusic> music,int position);
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