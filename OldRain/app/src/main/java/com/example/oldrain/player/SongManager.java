package com.example.oldrain.player;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Administrator on 14-7-24.
 */
public class SongManager {
    String paths;
    Context context;
    DataBase dataBase;
    private ArrayList<HashMap<String, Object>> songList = new ArrayList<HashMap<String, Object>>();

    public SongManager(String path, Context context){
        paths = path;
        this.context = context;
        ToolClass.DeleteFolder(Environment.getExternalStorageDirectory().toString() + "/OldRain/oldrain.db");
        dataBase = new DataBase(context);
    }

    public ArrayList<HashMap<String, Object>> oneKey(File filePath, String temp){
        ArrayList<HashMap<String, Object>> songLists = new ArrayList<HashMap<String, Object>>();
        songLists = search(filePath, temp);
        dataBase.close();
        return songLists;
    }
    ArrayList<HashMap<String, Object>> search(File filePath, String temp){


        StringBuilder filelist = new StringBuilder();
        File[] files = filePath.listFiles();

        if(files != null){
            for(int i=0; i<files.length; i++){
                if(files[i].isDirectory()){
                    search(files[i], temp);
                } else {
                    if(files[i].getName().toLowerCase().contains(temp)){
                        //filelist.append(files[i].getAbsolutePath() + ",");
                        HashMap<String, Object> song = new HashMap<String, Object>();
                        song.put("name", files[i].getName().substring(0, (files[i].getName().length() - 4)));
                        song.put("path", files[i].getPath());
                        song.put("playtag", "0");
                        song.put("lovetag", "0");
                        song.put("album", "jjl");
                        song.put("singer", "jjl");
                        songList.add(song);
                        //StickIn stickIn = new StickIn("d");
                        //stickIn.writeToSDcardFile("record.txt", "OldRain", song.get("name").toString()
                        //+ song.get("path").toString() + song.get("tag").toString() + song.get("album").toString()
                          //      + song.get("singer").toString() + "\n");
                        dataBase.saveData(song);
                    }
                }
            }
        }
        return songList;
    }

    public ArrayList<HashMap<String, Object>> getSongList(){
        File home = new File(paths);
        if(home.listFiles(new FileFilter()).length > 0){
            for(File file : home.listFiles(new FileFilter())){
                HashMap<String, Object> song = new HashMap<String, Object>();
                song.put("song_name", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("singer", file.getPath());
                song.put("play_tag", 0);
                song.put("favor_tag", false);
                songList.add(song);
            }
        }
        return songList;
    }
}

class  FileFilter implements FilenameFilter{
    public boolean accept(File dir, String name){
        return (name.toLowerCase().endsWith(".mp3"));
    }
}
