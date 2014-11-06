package com.example.oldrain.player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.oldrain.player.fragments.LyricFragment;
import com.example.oldrain.player.fragments.MoreFragment;
import com.example.oldrain.player.fragments.MusicHallFragment;
import com.example.oldrain.player.fragments.SearchFragment;
import com.example.oldrain.player.fragments.SongListFragment;
import com.example.oldrain.player.fragments.TotalListFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends FragmentActivity {

    SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager=getSupportFragmentManager();
    StickIn stickIn;
    MainReceiver mainReceiver;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        stickIn = new StickIn("just");
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "MainAcitivity Create" + "\n");
        sharedPreferences = getSharedPreferences(MidValue.ShareName, Activity.MODE_PRIVATE);
        MidValue.PLAY_MODEL = sharedPreferences.getInt("PLAYMODEL", MidValue.LOOP_MODEL);
        MidValue.Cur_Singer = sharedPreferences.getString("CURSINGER", MidValue.Cur_Singer);
        MidValue.Cur_SongName = sharedPreferences.getString("CURSONGNAME", MidValue.Cur_Singer);
        MidValue.Cur_SongPath = sharedPreferences.getString("CURSONGPATH", MidValue.Cur_Singer);
        MidValue.PlayerPosition = sharedPreferences.getInt("PLAYERPOSITION", 0);

        TotalListFragment totalListFragment = new TotalListFragment();
        FragmentTransaction total_list_ft = fragmentManager.beginTransaction();
        total_list_ft.add(R.id.homepage, totalListFragment, MidValue.SONG_TAG+"");
        total_list_ft.commit();

        db = new DataBase(this);
        MidValue.local_song = db.getData();
        db.close();

        mainReceiver = new MainReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.example.oldrain.player.MainActivity");
        registerReceiver(mainReceiver, filter);

        Intent intent = getIntent();
        String action = intent.getAction();
        if(intent.ACTION_VIEW.equals(action)){
            //处理传递来的参数，intent.getDataString()为参数
            //TextView tv = (TextView)findViewById(R.id.tvText);
            //tv.setText(intent.getDataString());
        }

        if(!MidValue.service_run){
            Intent intents = new Intent(this, MusicPlayer.class);
            this.startService(intents);
            MidValue.service_run = true;
        }
    }

    public class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            MidValue.frag_tag = bundle.getInt("tag");
            MidValue.frag_oldtag = bundle.getInt("oldtag");
            if(MidValue.frag_oldtag != MidValue.frag_tag){
                changeFragment(MidValue.frag_tag, MidValue.frag_oldtag);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            this.finish();
            this.onDestroy();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void changeFragment(int tags, int oldtag){

        switch (tags){
            case MidValue.SONG_TAG:
                TotalListFragment totalListFragment = new TotalListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        totalListFragment, MidValue.SONG_TAG, fragmentManager);
                break;
            case MidValue.MUSICHALL_TAG:
                MusicHallFragment musicHallFragment = new MusicHallFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        musicHallFragment, MidValue.MUSICHALL_TAG, fragmentManager);
                break;
            case MidValue.SEARCH_TAG:
                SearchFragment searchFragment= new SearchFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        searchFragment, MidValue.SEARCH_TAG, fragmentManager);
                break;
            case MidValue.MORE_TAG:
                MoreFragment moreFragment = new MoreFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        moreFragment, MidValue.MORE_TAG, fragmentManager);
                break;
            case MidValue.LOVELIST:
                SongListFragment songListFragment = new SongListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        songListFragment, MidValue.LOVELIST, fragmentManager);
                break;
            case MidValue.LOCALLIST:
                SongListFragment songListFragment1 = new SongListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        songListFragment1, MidValue.LOCALLIST, fragmentManager);
                break;
            case MidValue.DOWNLOADLIST:
                SongListFragment songListFragment2 = new SongListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        songListFragment2, MidValue.DOWNLOADLIST, fragmentManager);
                break;
            case MidValue.BACK_SONGLIST:
                TotalListFragment totalListFragment1 = new TotalListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        totalListFragment1, MidValue.SONG_TAG, fragmentManager);
                break;
            case MidValue.LYRIC:
                MidValue.FRAG_SWITCH_MODEL = MidValue.UP_OUT_FADE;
                LyricFragment lyricFragment = new LyricFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(oldtag+""),
                        lyricFragment, MidValue.LYRIC, fragmentManager);
                MidValue.FRAG_SWITCH_MODEL = MidValue.FADE_IN_OUT;
                break;
            case MidValue.LYRIC_BACK:
                if(MidValue.frag_oldtag == MidValue.SONG_TAG){
                    TotalListFragment totalListFragment2 = new TotalListFragment();
                    ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(MidValue.frag_tag+""),
                            totalListFragment2, MidValue.SONG_TAG, fragmentManager);
                }else{
                    SongListFragment songListFragment5 = new SongListFragment();
                    ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(MidValue.frag_tag+""),
                            songListFragment5, MidValue.frag_oldtag, fragmentManager);
                }
                break;
        }
        MidValue.frag_tag = tags;
        MidValue.frag_oldtag = oldtag;
    }

    private long exitTime =   0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(MidValue.LOVELIST == MidValue.frag_tag || MidValue.LOCALLIST == MidValue.frag_tag ||
                    MidValue.DOWNLOADLIST == MidValue.frag_tag){
                TotalListFragment totalListFragment = new TotalListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(MidValue.frag_tag+""),
                        totalListFragment, MidValue.SONG_TAG, fragmentManager);
                MidValue.frag_oldtag = MidValue.frag_tag;
                MidValue.frag_tag = MidValue.SONG_TAG;
                return true;
            }else if(MidValue.frag_tag == MidValue.LYRIC) {
                if(MidValue.frag_oldtag == MidValue.SONG_TAG){
                    TotalListFragment totalListFragment = new TotalListFragment();
                    ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(MidValue.frag_tag+""),
                            totalListFragment, MidValue.SONG_TAG, fragmentManager);
                }else{
                    SongListFragment songListFragment = new SongListFragment();
                    ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(MidValue.frag_tag+""),
                            songListFragment, MidValue.frag_oldtag, fragmentManager);
                }
                MidValue.frag_tag = MidValue.frag_oldtag;
                MidValue.frag_oldtag = MidValue.LYRIC;

                return true;
            }else{
                if((System.currentTimeMillis()-exitTime) > 2000){
                    Toast.makeText(getApplicationContext(), "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                    intent.addCategory(Intent.CATEGORY_HOME);
                    this.startActivity(intent);
                    return true;
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("PLAYMODEL", MidValue.PLAY_MODEL);
        editor.putInt("PLAYERPOSITION", MidValue.PlayerPosition);
        editor.putString("CURSONGNAME", MidValue.Cur_SongName);
        editor.putString("CURSINGER", MidValue.Cur_Singer);
        editor.putString("CURSONGPATH", MidValue.Cur_SongPath);
        editor.apply();

        unregisterReceiver(mainReceiver);
        stickIn.writeToSDcardFile("record.txt", "OldRain", "MainAcitivity Destroy" + "\n");

        if(MidValue.PlayedOne){
            Intent intents = new Intent(this, MusicPlayer.class);
            this.stopService(intents);
        }
        super.onDestroy();
    }
}
