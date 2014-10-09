package com.example.oldrain.player;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 14-7-23.
 */
public class SongListFragment extends ListFragment {

    private Activity home_activity;
    private ListView list;
    private String searchPath;
    SongListAdapter listItemAdapter;
    ArrayList<HashMap<String, Object>> listItem;
    DataBase dataBase;
    StickIn stickIn;
    ImageButton backup, sync;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        stickIn = new StickIn("just");
        home_activity=activity;
        searchPath = Environment.getExternalStorageDirectory().toString();
        listItem = new ArrayList<HashMap<String, Object>>();
        dataBase = new DataBase(home_activity);
        switch (Integer.parseInt(ToolClass.tag)){
            case 511:listItem = dataBase.getData("MYLOVE");break;
            case 512:listItem = dataBase.getData("LOCAL");break;
            case 513:listItem = dataBase.getData("DOWNLOAD");break;
            case 514:listItem = dataBase.getData("RECENT");break;
        }/**/
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "ListAcitivity Create" + "\n");

        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SongListAdapter(home_activity, listItem,//数据源
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

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", ToolClass.tag + "0");
                intent.putExtra("oldtag", "1");
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sync.setImageDrawable(getResources().getDrawable(R.drawable.music_sync_disabled));

                switch (Integer.parseInt(ToolClass.tag)){
                    case 511:break;
                    case 512: SongManager local_music = new SongManager(searchPath, home_activity);
                        listItem.clear();
                        listItem.addAll(local_music.oneKey(new File(searchPath), ".mp3"));
                        listItemAdapter.notifyDataSetChanged();break;
                    case 513:break;
                    case 514:break;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if(position != ToolClass.PlayerPosition){
            if(ToolClass.PlayedOne){
                HashMap<String, Object> song = new HashMap<String, Object>();
                song.put("name", listItem.get(position).get("name"));
                song.put("singer", listItem.get(position).get("singer"));
                song.put("playtag", "1");
                song.put("path", listItem.get(position).get("path"));
                song.put("album", listItem.get(position).get("album"));
                song.put("lovetag", listItem.get(position).get("lovetag"));
                listItem.set(position, song);

                HashMap<String, Object> song2 = new HashMap<String, Object>();
                song2.put("name", listItem.get(ToolClass.PlayerPosition).get("name"));
                song2.put("singer", listItem.get(ToolClass.PlayerPosition).get("singer"));
                song2.put("playtag", "0");
                song2.put("lovetag", listItem.get(ToolClass.PlayerPosition).get("lovetag"));
                song2.put("path", listItem.get(ToolClass.PlayerPosition).get("path"));
                song2.put("album", listItem.get(ToolClass.PlayerPosition).get("album"));
                listItem.set(ToolClass.PlayerPosition, song2);
            }else{
                HashMap<String, Object> song2 = new HashMap<String, Object>();
                song2.put("name", listItem.get(position).get("name"));
                song2.put("singer", listItem.get(position).get("singer"));
                song2.put("playtag", "1");
                song2.put("path", listItem.get(position).get("path"));
                song2.put("album", listItem.get(position).get("album"));
                song2.put("lovetag", listItem.get(position).get("lovetag"));
                listItem.set(position, song2);
            }
            listItemAdapter.notifyDataSetChanged();
        }/**/

        if(position != ToolClass.PlayerPosition){
            Intent intent = new Intent();
            intent.putExtra("path", listItem.get(position).get("path").toString());
            intent.putExtra("tag", "other");
            intent.setAction("com.example.oldrain.player.MusicPlayer");
            home_activity.sendBroadcast(intent);
            ToolClass.Playing = true;
        }else{
            if(ToolClass.Playing){
                Intent intent = new Intent();
                intent.putExtra("path", listItem.get(position).get("path").toString());
                intent.putExtra("tag", "pause");
                intent.setAction("com.example.oldrain.player.MusicPlayer");
                home_activity.sendBroadcast(intent);

                ToolClass.Playing = false;
            } else {
                Intent intent = new Intent();
                intent.putExtra("path", listItem.get(position).get("path").toString());
                intent.putExtra("tag", "play");
                intent.setAction("com.example.oldrain.player.MusicPlayer");
                home_activity.sendBroadcast(intent);

                ToolClass.Playing = true;
            }
        }
        ToolClass.PlayerPosition = position;
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
