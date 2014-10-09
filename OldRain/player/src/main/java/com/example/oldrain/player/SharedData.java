package com.example.oldrain.player;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Untold on 2014/9/9.
 */
public class SharedData {
    //private SharedPreferences sharedPreferences;
    private String tag = "1", oldtag = "1";
    public SharedData(){
        this.oldtag = "1";
        this.oldtag = "1";
    }
    public SharedData(String tag, String oldtag){
        this.tag = tag;
        this.oldtag = oldtag;
        ToolClass.tag = tag;
        ToolClass.oldtag = oldtag;
        //sharedPreferences = activity.getSharedPreferences("MidValue", Activity.MODE_PRIVATE);
    }

    public void setTag(String tag){
        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tag", tag);
        editor.putString("oldtag", oldtag);
        editor.commit();*/
        this.tag = tag;
        ToolClass.tag = tag;
    }

    public void setOldtag(String oldtag){
        this.oldtag = oldtag;
        ToolClass.oldtag = oldtag;
    }

    public String getTag(){
        return tag;
    }

    public String getOldtag(){
        return oldtag;
    }
}
