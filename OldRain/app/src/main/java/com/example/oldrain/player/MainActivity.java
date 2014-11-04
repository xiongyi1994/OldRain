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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        stickIn = new StickIn("just");
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "MainAcitivity Create" + "\n");
        sharedPreferences = getSharedPreferences(MidValue.ShareName, Activity.MODE_PRIVATE);
        MidValue.PLAY_MODEL = sharedPreferences.getInt("PLAYMODEL", MidValue.LOOP_MODEL);

        TotalListFragment totalListFragment = new TotalListFragment();
        FragmentTransaction total_list_ft = fragmentManager.beginTransaction();
        total_list_ft.add(R.id.homepage, totalListFragment, MidValue.SONG_TAG+"");
        total_list_ft.commit();

        MidValue.local_song = new ArrayList<HashMap<String, Object>>();

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

        /*int i = 0;
        DataBase dataBase = new DataBase(this);
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (null != cursor && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                i++;
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
                dataBase.saveData(song, "LOCAL");
            }
        }
        ToolClass.show(i+"", this);*/
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
        MidValue.frag_tag = tags;
        MidValue.frag_oldtag = oldtag;
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
        }
    }

    private long exitTime =   0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(MidValue.LOVELIST != MidValue.frag_tag && MidValue.LOCALLIST != MidValue.frag_tag &&
                    MidValue.DOWNLOADLIST != MidValue.frag_tag){
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
            }else{
                TotalListFragment totalListFragment = new TotalListFragment();
                ToolClass.switchContent(getSupportFragmentManager().findFragmentByTag(MidValue.frag_tag+""),
                        totalListFragment, MidValue.SONG_TAG, fragmentManager);
                MidValue.frag_oldtag = MidValue.frag_tag;
                MidValue.frag_tag = MidValue.SONG_TAG;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("PLAYMODEL", MidValue.PLAY_MODEL);
        editor.apply();

        unregisterReceiver(mainReceiver);
        stickIn.writeToSDcardFile("record.txt", "OldRain", "MainAcitivity Destroy" + "\n");
        super.onDestroy();
    }
}
