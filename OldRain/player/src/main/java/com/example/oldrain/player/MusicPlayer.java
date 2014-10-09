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
        if(!player.isLooping()){
            player.setLooping(true);
        }
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.example.oldrain.player.MusicPlayer");
        registerReceiver(receiver, filter);

        /*player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                /*stickIn.writeToSDcardFile("record.txt", "OldRain",
                        ToolClass.listItem.get(ToolClass.PlayerPosition + 1).get("song_name") + "\n" +
                                ToolClass.listItem.get(ToolClass.PlayerPosition + 1).get("singer") + "\n");
                if(ToolClass.PlayedOne){
                    player.reset();
                } else {
                    ToolClass.PlayedOne = true;
                }

                if(ToolClass.PlayerPosition != ToolClass.listItem.size()){
                    path = ToolClass.listItem.get(ToolClass.PlayerPosition + 1).get("path").toString();
                } else {
                    path = ToolClass.listItem.get(0).get("path").toString();
                }

                try {
                    player.setDataSource(path);
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
                ToolClass.PlayerPosition = ToolClass.PlayerPosition + 1;
            }
        });*/

        return super.onStartCommand(intent, flags, startId);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            path = bundle.getString("path");
            tag = bundle.getString("tag");

            if(tag.equals("play")){
                if(!player.isPlaying()){
                    /*try{
                        player.setDataSource(path);
                        player.prepare();
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    player.start();
                }
            } else if(tag.equals("pause")){
                if(player.isPlaying()){
                    time = player.getCurrentPosition();
                    player.pause();
                }
            } else if(tag.equals("other")){
                if(ToolClass.PlayedOne){
                    player.reset();
                } else {
                    ToolClass.PlayedOne = true;
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
