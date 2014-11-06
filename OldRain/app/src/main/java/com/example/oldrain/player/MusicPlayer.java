package com.example.oldrain.player;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Untold on 14-8-1.
 */
public class MusicPlayer extends Service {

    private MediaPlayer player;
    String path, tag;
    int time;
    MyReceiver receiver;
    StickIn stickIn;
    @Override
    public void onCreate() {
        super.onCreate();
        stickIn = new StickIn("just");
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "Service Created" + "\n");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
        StickIn stickIn = new StickIn("just");
        stickIn.writeToSDcardFile("record.txt", "OldRain", "Service Destroyed" + "\n");
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "Service onStartCommand" + "\n");
        player = new MediaPlayer();
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.example.oldrain.player.MusicPlayer");
        registerReceiver(receiver, filter);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(MidValue.PlayedOne){
                    player.reset();
                } else {
                    MidValue.PlayedOne = true;
                }

                switch (MidValue.PLAY_MODEL){
                    case MidValue.JUSTONE_MODEL:
                        break;
                    case MidValue.ONELOOP_MODEL:
                        path = MidValue.local_song.get(MidValue.PlayerPosition).get("path").toString();
                        break;
                    case MidValue.RANDOM_MODEL:
                        MidValue.PlayerPosition = ToolClass.randomInt(MidValue.local_song.size()-1);
                        path = MidValue.local_song.get(MidValue.PlayerPosition).get("path").toString();
                        break;
                    case MidValue.LOOP_MODEL:
                        if(MidValue.PlayerPosition != (MidValue.local_song.size()-1)){
                            path = MidValue.local_song.get(MidValue.PlayerPosition + 1).get("path").toString();
                            MidValue.PlayerPosition = MidValue.PlayerPosition + 1;
                        } else {
                            path = MidValue.local_song.get(0).get("path").toString();
                            MidValue.PlayerPosition = 0;
                        }
                        break;
                }
                if (MidValue.PLAY_MODEL != MidValue.JUSTONE_MODEL)
                    ToolClass.changeCurSongInfo(MidValue.PlayerPosition);
                if(MidValue.PLAY_MODEL != MidValue.JUSTONE_MODEL){
                    try {
                        player.setDataSource(path);
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.start();
                }

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            path = bundle.getString("path");
            tag = bundle.getString("tag");

            if(tag.equals("play")){
                if(!MidValue.PlayedOne){
                    for(int i=0; i<MidValue.local_song.size(); i++){
                        if(MidValue.local_song.get(i).get("path").equals(MidValue.Cur_SongPath)){
                            MidValue.PlayerPosition = i;
                            break;
                        }
                    }
                    try{
                        player.setDataSource(path);
                        player.prepare();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    player.start();
                    MidValue.PlayedOne = true;
                }else if(!player.isPlaying()){
                    player.start();
                }
            } else if(tag.equals("pause")){
                if(player.isPlaying()){
                    time = player.getCurrentPosition();
                    player.pause();
                }
            } else if(tag.equals("other")){
                if(MidValue.PlayedOne){
                    player.reset();
                } else {
                    MidValue.PlayedOne = true;
                }

                try {
                    player.setDataSource(path);
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        path = intent.getStringExtra("path");
        return null;
    }
}
