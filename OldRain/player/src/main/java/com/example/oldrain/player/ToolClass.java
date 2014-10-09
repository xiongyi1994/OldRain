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
 */
public class ToolClass {
    Context context;
    static String Tag = "1";
    static int PlayerPosition = -1;
    static boolean Playing = false;
    static boolean PlayedOne = false;
    static boolean service_run = false;
    static String tag = "1", oldtag = "1";
    static ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

    public ToolClass(Context context){

    }
    static void show(String string, Context context){
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
    public static void switchContent(boolean back_tag, Fragment from, Fragment to, String tag, FragmentManager fragmentManager) {
        if (true) {
            // mContent = to;
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.homepage, to, tag); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to); // 隐藏当前的fragment，显示下一个
            }
            if(back_tag){
                transaction.addToBackStack(null);
            }
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
}
