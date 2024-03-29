package com.example.oldrain.player.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.oldrain.player.DataBase;
import com.example.oldrain.player.MidValue;
import com.example.oldrain.player.R;
import com.example.oldrain.player.SongListAdapter;
import com.example.oldrain.player.SongManager;
import com.example.oldrain.player.StickIn;
import com.example.oldrain.player.ToolClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 14-7-23.
 * show the song list
 */
public class SongListFragment extends ListFragment {

    private Activity home_activity;
    private ListView list;
    private String searchPath;
    SongListAdapter listItemAdapter;
    DataBase dataBase;
    StickIn stickIn;
    ImageButton backup, sync, play_model, song_image, play_pause;
    TextView song_name, singer;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        stickIn = new StickIn("just");
        home_activity=activity;
        searchPath = Environment.getExternalStorageDirectory().toString();

        dataBase = new DataBase(home_activity);
        switch (MidValue.frag_tag){
            case MidValue.LOVELIST:
                MidValue.local_song = dataBase.getData();
                int length = MidValue.local_song.size();
                for(int i = 0; i<length; i++){
                    if(!MidValue.local_song.get(i).get("lovetag").equals("1")){
                        MidValue.local_song.remove(i);
                        i--;
                        length--;
                    }
                }
                break;
            case MidValue.LOCALLIST:
                MidValue.local_song = dataBase.getData();break;
            case MidValue.DOWNLOADLIST:
                MidValue.local_song = dataBase.getData();
                //此处添加 对 下载过的歌曲的过滤代码
                break;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "ListAcitivity Create" + "\n");

        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SongListAdapter(home_activity, MidValue.local_song,//数据源
                R.layout.song_items_layout,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new int[] {R.id.play_tag, R.id.song_name, R.id.singer, R.id.favor_tag});
        //添加并且显示
        setListAdapter(listItemAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.song_list_layout, container,
                false);

        list = (ListView) home_activity.findViewById(android.R.id.list);
        searchPath = Environment.getExternalStorageDirectory().toString();
        backup = (ImageButton) rootView.findViewById(R.id.backup);
        sync = (ImageButton) rootView.findViewById(R.id.sync);
        play_model = (ImageButton) rootView.findViewById(R.id.model);
        setImage();

        play_pause = (ImageButton) rootView.findViewById(R.id.play_pause);
        if(MidValue.Playing){
            play_pause.setImageDrawable(home_activity.getResources().getDrawable(R.drawable.pause));
        }else{
            play_pause.setImageDrawable(home_activity.getResources().getDrawable(R.drawable.play));
        }
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MidValue.Playing){
                    play_pause.setImageDrawable(home_activity.getResources().getDrawable(R.drawable.play));
                    Intent intent = new Intent();
                    intent.putExtra("path", MidValue.Cur_SongPath);
                    intent.putExtra("tag", "pause");
                    intent.setAction("com.example.oldrain.player.MusicPlayer");
                    home_activity.sendBroadcast(intent);
                    MidValue.Playing = false;
                } else{
                    play_pause.setImageDrawable(home_activity.getResources().getDrawable(R.drawable.pause));
                    Intent intent = new Intent();
                    intent.putExtra("path", MidValue.Cur_SongPath);
                    intent.putExtra("tag", "play");
                    intent.setAction("com.example.oldrain.player.MusicPlayer");
                    home_activity.sendBroadcast(intent);
                    MidValue.Playing = true;
                }
            }
        });

        song_name = (TextView) rootView.findViewById(R.id.song_names);
        singer = (TextView) rootView.findViewById(R.id.singers);
        song_name.setText(MidValue.Cur_SongName);
        singer.setText(MidValue.Cur_Singer);
        song_image = (ImageButton) rootView.findViewById(R.id.song_image);

        song_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", MidValue.LYRIC);
                intent.putExtra("oldtag", MidValue.frag_tag);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", MidValue.SONG_TAG);
                intent.putExtra("oldtag", MidValue.frag_tag);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongManager local_music = new SongManager(searchPath, home_activity);
                local_music.refreshFromSysDB();
                MidValue.local_song.clear();
                MidValue.local_song.addAll(dataBase.getData());
                //MidValue.local_song.addAll(local_music.oneKey(new File(searchPath), ".mp3"));
                listItemAdapter.notifyDataSetChanged();
            }
        });
        play_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeModel();
                setImage();
            }
        });
        return rootView;
    }

    void changeModel(){
        if(MidValue.PLAY_MODEL == MidValue.JUSTONE_MODEL){
            MidValue.PLAY_MODEL = MidValue.RANDOM_MODEL;
        }else{
            MidValue.PLAY_MODEL++;
        }
    }
    void setImage(){
        switch (MidValue.PLAY_MODEL){
            case MidValue.LOOP_MODEL:
                play_model.setImageResource(R.drawable.ic_action_loop);
                break;
            case MidValue.ONELOOP_MODEL:
                play_model.setImageResource(R.drawable.ic_action_oneloop);
                break;
            case MidValue.RANDOM_MODEL:
                play_model.setImageResource(R.drawable.ic_action_random);
                break;
            case MidValue.JUSTONE_MODEL:
                play_model.setImageResource(R.drawable.ic_action_justone);
                break;
        }
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if(!MidValue.local_song.get(position).get("path").equals(MidValue.Cur_SongPath)){
            Intent intent = new Intent();
            intent.putExtra("path", MidValue.local_song.get(position).get("path").toString());
            intent.putExtra("tag", "other");
            intent.setAction("com.example.oldrain.player.MusicPlayer");
            home_activity.sendBroadcast(intent);
            MidValue.Playing = true;
        }else{
            if(MidValue.Playing){
                Intent intent = new Intent();
                intent.putExtra("path", MidValue.local_song.get(position).get("path").toString());
                intent.putExtra("tag", "pause");
                intent.setAction("com.example.oldrain.player.MusicPlayer");
                home_activity.sendBroadcast(intent);

                MidValue.Playing = false;
            } else {
                Intent intent = new Intent();
                intent.putExtra("path", MidValue.local_song.get(position).get("path").toString());
                intent.putExtra("tag", "play");
                intent.setAction("com.example.oldrain.player.MusicPlayer");
                home_activity.sendBroadcast(intent);

                MidValue.Playing = true;
            }
        }
        MidValue.PlayerPosition = position;

        if(MidValue.Playing){
            play_pause.setImageDrawable(home_activity.getResources().getDrawable(R.drawable.pause));
        }else{
            play_pause.setImageDrawable(home_activity.getResources().getDrawable(R.drawable.play));
        }

        ToolClass.changeCurSongInfo(position);
        song_name.setText(MidValue.Cur_SongName);
        singer.setText(MidValue.Cur_Singer);

        listItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy(){

        dataBase.close();
        super.onDestroy();
    }
}
