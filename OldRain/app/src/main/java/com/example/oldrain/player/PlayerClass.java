package com.example.oldrain.player;

import android.media.MediaPlayer;

/**
 * Created by Untold on 14-8-1.
 */
public class PlayerClass {
    MediaPlayer player;
    int time;
    public PlayerClass(){
        player = new MediaPlayer();
    }

    public void play(String path){
        if(!player.isPlaying()){
            try{
                player.setDataSource(path);
                player.prepare();
            }catch (Exception e){
                e.printStackTrace();
            }
            player.start();
        }
    }

    public void pause(){
        if(player.isPlaying()){
            time = player.getCurrentPosition();
            player.pause();
        }
    }

    public void resume(){

    }
}
