package com.example.oldrain.player.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.oldrain.player.MidValue;
import com.example.oldrain.player.MusicPlayer;
import com.example.oldrain.player.R;
import com.example.oldrain.player.StickIn;
import com.example.oldrain.player.ToolClass;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 14-7-24.
 * show the several song list
 */
public class TotalListFragment extends ListFragment{
    Activity home_activity;
    ListView listView;
    StickIn stickIn;
    private LinearLayout song_list, music_hall, search, more;
    private ImageButton song_list_button, music_hall_button, search_button, more_button, song_image,
            play_pause;
    private TextView song_list_text, music_hall_text, search_text, more_text, song_name, singer;;
    ArrayList<HashMap<String, Object>> total_list;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        stickIn = new StickIn("just");
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "Totalfragment Attach\n");
        home_activity=activity;

        total_list = getData();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "Totalfragmetn Create" + "\n");

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter= new SimpleAdapter(home_activity, total_list,//数据源
                R.layout.total_item_layout,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"icon", "list_name", "song_num"},
                new int[] {R.id.icon, R.id.four, R.id.side_title});
        //添加并且显示
        setListAdapter(listItemAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.total_list_layout, container,
                false);
        listView = (ListView) home_activity.findViewById(android.R.id.list);

        initView(rootView);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent();
        switch(position){
            case 0:
                intent.putExtra("tag", MidValue.LOVELIST);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
                break;
            case 1:
                intent.putExtra("tag", MidValue.LOCALLIST);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
                break;
            case 2:
                intent.putExtra("tag", MidValue.DOWNLOADLIST);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "Totalfragment onActivityCreate" + "\n");
    }

    ArrayList<HashMap<String, Object>> getData(){
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        HashMap<String, Object> map3 = new HashMap<String, Object>();

        map.put("icon", R.drawable.i_like);
        map.put("list_name", "我喜欢");
        map.put("song_num", ToolClass.getLovedSongCount()+"首");
        listItem.add(map);

        map1.put("icon", R.drawable.music_all);
        map1.put("list_name", "本地歌曲");
        map1.put("song_num", MidValue.TotalSongCount+"首");
        listItem.add(map1);

        map2.put("icon", R.drawable.downloaded);
        map2.put("list_name", "已下载");
        map2.put("song_num", MidValue.TotalSongCount+"首");
        listItem.add(map2);

        return listItem;
    }

    @Override
    public void onDestroy(){
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "TotalFragment Destroy" + "\n");
        super.onDestroy();
    }

    void initView(View view){
        song_list = (LinearLayout) view.findViewById(R.id.song_list);
        music_hall = (LinearLayout) view.findViewById(R.id.music_hall);
        search = (LinearLayout) view.findViewById(R.id.search);
        more = (LinearLayout) view.findViewById(R.id.more);
        song_image = (ImageButton) view.findViewById(R.id.song_image);

        play_pause = (ImageButton) view.findViewById(R.id.play_pause);
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

        song_name = (TextView) view.findViewById(R.id.song_names);
        singer = (TextView) view.findViewById(R.id.singers);
        song_name.setText(MidValue.Cur_SongName);
        singer.setText(MidValue.Cur_Singer);

        song_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", MidValue.LYRIC);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        music_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", MidValue.MUSICHALL_TAG);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", MidValue.SEARCH_TAG);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tag", MidValue.MORE_TAG);
                intent.putExtra("oldtag", MidValue.SONG_TAG);
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });

        song_list_button = (ImageButton) view.findViewById(R.id.song_list_button);
        music_hall_button = (ImageButton) view.findViewById(R.id.music_hall_button);
        search_button = (ImageButton) view.findViewById(R.id.search_button);
        more_button = (ImageButton) view.findViewById(R.id.more_button);

        song_list_text = (TextView) view.findViewById(R.id.song_list_text);
        music_hall_text = (TextView) view.findViewById(R.id.music_hall_text);
        search_text = (TextView) view.findViewById(R.id.search_text);
        more_text = (TextView) view.findViewById(R.id.more_text);

        ToolClass.setBackgroundColor(song_list, song_list_text, song_list_button,
                "1", 0, home_activity);
        ToolClass.setBackgroundColor(music_hall, music_hall_text, music_hall_button,
                "2", 1, home_activity);
        ToolClass.setBackgroundColor(search, search_text, search_button,
                "3", 1,home_activity);
        ToolClass.setBackgroundColor(more, more_text, more_button,
                "4", 1, home_activity);
    }
}
