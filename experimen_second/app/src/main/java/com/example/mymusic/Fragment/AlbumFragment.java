package com.example.mymusic.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymusic.Activity.Play;
import com.example.mymusic.Entity.LocalMusic;
import com.example.mymusic.R;

public class AlbumFragment extends Fragment{
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container,false);
        imageView = view.findViewById(R.id.imageView10);
        imageView.setImageBitmap(((Play) getActivity()).localMusic.getBitmap());
        return view;
    }

    public void AFragment(LocalMusic localMusic){
        Bundle bundle = new Bundle();
        bundle.putParcelable("localMusic",localMusic);
        this.setArguments(bundle);
    }

    public void updateUI(Bitmap bm){
        imageView.setImageBitmap(bm);
    }

}
