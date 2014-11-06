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

    public void refreshFromSysDB(){
        MidValue.TotalSongCount = 0;
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (null != cursor && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                //歌曲的名称 ：MediaStore.Audio.Media.TITLE
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                HashMap<String, Object> song = new HashMap<String, Object>();
                song.put("name", title);
                song.put("path", url);
                song.put("playtag", "0");
                song.put("lovetag", "0");
                song.put("album", album);
                song.put("singer", artist);
                dataBase.saveData(song);
                MidValue.TotalSongCount++;
            }
        }
        dataBase.close();
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
