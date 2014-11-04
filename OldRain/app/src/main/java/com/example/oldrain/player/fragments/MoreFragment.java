package com.example.oldrain.player.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oldrain.player.R;
import com.example.oldrain.player.ToolClass;

/**
 * Created by Administrator on 14-7-23.
 */
public class MoreFragment extends Fragment {

    Activity home_activity;
    private LinearLayout song_list, music_hall, search, more;
    private ImageButton song_list_button, music_hall_button, search_button, more_button;
    private TextView song_list_text, music_hall_text, search_text, more_text;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        home_activity=activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.more_layout, container,
                false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    void initView(View view){
        song_list = (LinearLayout) view.findViewById(R.id.song_list);
        music_hall = (LinearLayout) view.findViewById(R.id.music_hall);
        search = (LinearLayout) view.findViewById(R.id.search);
        more = (LinearLayout) view.findViewById(R.id.more);

        song_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolClass.show("total", home_activity);
                Intent intent = new Intent();
                intent.putExtra("tag", "1");
                intent.putExtra("oldtag", "4");
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        music_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolClass.show("musichall", home_activity);
                Intent intent = new Intent();
                intent.putExtra("tag", "2");
                intent.putExtra("oldtag", "4");
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolClass.show("search", home_activity);
                Intent intent = new Intent();
                intent.putExtra("tag", "3");
                intent.putExtra("oldtag", "4");
                intent.setAction("com.example.oldrain.player.MainActivity");
                home_activity.sendBroadcast(intent);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolClass.show("more", home_activity);
                Intent intent = new Intent();
                intent.putExtra("tag", "4");
                intent.putExtra("oldtag", "4");
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
                "1", 1, home_activity);
        ToolClass.setBackgroundColor(music_hall, music_hall_text, music_hall_button,
                "2", 1, home_activity);
        ToolClass.setBackgroundColor(search, search_text, search_button,
                "3", 1,home_activity);
        ToolClass.setBackgroundColor(more, more_text, more_button,
                "4", 0, home_activity);/**/
    }
}
