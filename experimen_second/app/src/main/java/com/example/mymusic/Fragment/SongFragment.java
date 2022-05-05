package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymusic.R;
import com.example.mymusic.Utils.GetOnlineJSON;

public class SongFragment extends Fragment {
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song,container,false);
        textView = view.findViewById(R.id.textView8);
        ScrollView scrollView = view.findViewById(R.id.scrollView3);


        textView.setText("阳春三月初满枝迎春新花栖木\n" +
                "天留片片白云风上住\n" +
                "孩童推门去又放纸鸢笑声满路\n" +
                "手中长线没入天尽处\n" +
                "谁人悄约时恰得一片桃华满目\n" +
                "手边流云与落英相逐\n" +
                "河水桥下淌风倚柳青岸上住\n" +
                "鸟儿绕纸鸢 声声诉\n" +
                "三月来百草开盈香满袖万物苏\n" +
                "虫鸣和着欢笑 心事舒\n" +
                "三月来暖阳复 相携去 踏青处\n" +
                "陌上花开满路 香入土\n" +
                "三月来有归人 马踏浅草声催促\n" +
                "春有期归有日 今归途\n" +
                "三月来生情愫 春刚复 情入骨\n" +
                "借缕东风互诉 相爱慕\n" +
                "谁人悄约时恰得一片桃华满目\n" +
                "手边流云与落英相逐\n" +
                "河水桥下淌风倚柳青岸上住\n" +
                "鸟儿绕纸鸢 声声诉\n" +
                "三月来百草开盈香满袖万物苏\n" +
                "虫鸣和着欢笑 心事舒\n" +
                "三月来暖阳复 相携去 踏青处\n" +
                "陌上花开满路 香入土\n" +
                "三月来有归人 马踏浅草声催促\n" +
                "春有期归有日 今归途\n" +
                "三月来生情愫 春刚复 情入骨\n" +
                "借缕东风互诉 相爱慕\n" +
                "阳春三月初 正是人间好花簇簇\n" +
                "人逢此景欢喜由心处\n" +
                "阳春三月来 自有生命破尘土\n" +
                "送来希望事 好运出\n");
        return view;
    }
}
