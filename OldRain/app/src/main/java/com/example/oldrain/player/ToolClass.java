package com.example.oldrain.player;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 14-7-23.
 * Tool Class
 */
public class ToolClass {

    public static void show(String string, Context context){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static String getTime(){
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss ");
        Date curDate   =   new Date(System.currentTimeMillis());//获取当前时间
        String   str   =   formatter.format(curDate);
        return str;
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } /*else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }*/
        }
        return flag;
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static int getLovedSongCount(){
        int count = 0, list_size = MidValue.local_song.size();
        for(int i=0; i<list_size; i++){
            if(MidValue.local_song.get(i).get("lovetag").equals("1"))
                count++;
        }
        return count;
    }

    public static void changeCurSongInfo(int position){
        MidValue.Cur_SongPath = MidValue.local_song.get(position).get("path").toString();
        MidValue.Cur_Singer = MidValue.local_song.get(position).get("singer").toString();
        MidValue.Cur_SongName = MidValue.local_song.get(position).get("name").toString();
    }
    public static void switchContent(Fragment from, Fragment to, int tag, FragmentManager fragmentManager) {
        // mContent = to;
        FragmentTransaction transaction = null;
        switch (MidValue.FRAG_SWITCH_MODEL){
            case MidValue.FADE_IN_OUT:
                transaction = fragmentManager.beginTransaction().setCustomAnimations(
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case MidValue.LEFT_OUT_RIGHT_IN:
                transaction = fragmentManager.beginTransaction().setCustomAnimations(
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case MidValue.UP_OUT_FADE:
                transaction = fragmentManager.beginTransaction().setCustomAnimations(
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }

        if (!to.isAdded()) {    // 先判断是否被add过
            if (transaction != null) {
                transaction.hide(from).add(R.id.homepage, to, tag+""); // 隐藏当前的fragment，add下一个到Activity中
            }
        } else {
            if (transaction != null) {
                transaction.hide(from).show(to); // 隐藏当前的fragment，显示下一个
            }
        }
        if (transaction != null) {
            transaction.commit();
        }
    }

    public static void setBackgroundColor(LinearLayout l, TextView text, ImageButton i, String t, int c,Activity activity){
        if(c == 0){
            if(t == "1"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.loved));
                text.setTextColor(activity.getResources().getColor(R.color.white));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.song_list_clicked));
            }else if(t == "2"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.loved));
                text.setTextColor(activity.getResources().getColor(R.color.white));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.music_hall_clicked));
            }else if(t == "3"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.loved));
                text.setTextColor(activity.getResources().getColor(R.color.white));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.search_clicked));
            }else if(t == "4"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.loved));
                text.setTextColor(activity.getResources().getColor(R.color.white));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.more_clicked));
            }
        } else if (c == 1){
            if(t == "1"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.top_bar));
                text.setTextColor(activity.getResources().getColor(R.color.download_false));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.song_list));
            }else if(t == "2"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.top_bar));
                text.setTextColor(activity.getResources().getColor(R.color.download_false));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.music_hall));
            }else if(t == "3"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.top_bar));
                text.setTextColor(activity.getResources().getColor(R.color.download_false));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.search));
            }else if(t == "4"){
                l.setBackgroundColor(activity.getResources().getColor(R.color.top_bar));
                text.setTextColor(activity.getResources().getColor(R.color.download_false));
                i.setImageDrawable(activity.getResources().getDrawable(R.drawable.more));
            }
        }
    }

    public static int randomInt(int x){
        return (int)(Math.random() * (x+1));
    }
}
