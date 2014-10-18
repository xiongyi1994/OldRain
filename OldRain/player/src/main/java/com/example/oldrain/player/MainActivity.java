package com.example.oldrain.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends FragmentActivity implements  OnGestureListener, OnTouchListener {

    private FrameLayout fragmentLayout;
    private FragmentManager fragmentManager=getSupportFragmentManager();
    private final int MINI_DISTANCE = 120;
    private final int MINI_VELOCITY = 200;
    private GestureDetector gestureDetector = new GestureDetector(this);
    ArrayList<HashMap<String, String>> songList = new ArrayList<HashMap<String, String>>();
    StickIn stickIn;
    MainReceiver mainReceiver;
    SharedData sharedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        stickIn = new StickIn("just");
        //stickIn.writeToSDcardFile("record.txt", "OldRain", "MainAcitivity Create" + "\n");
        fragmentLayout = (FrameLayout) findViewById(R.id.homepage);
        fragmentLayout.setOnTouchListener(this);
        fragmentLayout.setLongClickable(true);

        TotalListFragment totalListFragment = new TotalListFragment();
        FragmentTransaction total_list_ft = fragmentManager.beginTransaction();
        total_list_ft.add(R.id.homepage, totalListFragment, ToolClass.Tag);
        total_list_ft.commit();/**/

        mainReceiver = new MainReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.example.oldrain.player.MainActivity");
        registerReceiver(mainReceiver, filter);

        sharedData = new SharedData();
    }

    public class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            sharedData.setTag(bundle.getString("tag"));
            sharedData.setOldtag(bundle.getString("oldtag"));
            if(!sharedData.getOldtag().equals(sharedData.getTag())){
                changeFragment(sharedData.getTag(), sharedData.getOldtag());
            }
        }
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度（像素/秒）
        // velocityY：Y轴上的移动速度（像素/秒）

        // X轴的坐标位移大于MINI_DISTANCE，且移动速度大于MINI_VELOCITY个像素/秒
        //向右翻图片
        int tag = Integer.parseInt(sharedData.getTag()), oldtag = Integer.parseInt(sharedData.getOldtag());
        if (e1.getX() - e2.getX() > MINI_DISTANCE
                && Math.abs(velocityX) > MINI_VELOCITY) {
            switch (tag){
                case 4:changeFragment("1", "4");
                    sharedData.setTag("1");sharedData.setOldtag("4");break;
                case 1:changeFragment((tag+1)+"", tag+"");
                    sharedData.setTag("2");sharedData.setOldtag("1");break;
                case 2:changeFragment((tag+1)+"", tag+"");
                    sharedData.setTag("3");sharedData.setOldtag("2");break;
                case 3:changeFragment((tag+1)+"", tag+"");
                    sharedData.setTag("4");sharedData.setOldtag("3");break;
                case 5:break;
                case 6:break;
            }
        }
        //向左翻图片
        if (e2.getX() - e1.getX() > MINI_DISTANCE
                && Math.abs(velocityX) > MINI_VELOCITY) {
            switch (tag){
                case 1:changeFragment("4", "1");
                    sharedData.setTag("4");sharedData.setOldtag("1");break;
                case 2:changeFragment((tag-1)+"", tag+"");
                    sharedData.setTag("1");sharedData.setOldtag("2");break;
                case 3:changeFragment((tag-1)+"", tag+"");
                    sharedData.setTag("2");sharedData.setOldtag("3");break;
                case 4:changeFragment((tag-1)+"", tag+"");
                    sharedData.setTag("3");sharedData.setOldtag("4");break;
                case 5:changeFragment("5120", "1");
                    sharedData.setTag("1");sharedData.setOldtag("5120");break;
                case 6:break;
            }
        }
        return false;
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void changeFragment(String tags, String oldtag){
        int int_tag = Integer.parseInt(tags);
        //ToolClass.show(int_tag + "/" + tags, getApplicationContext());
        switch (int_tag){
            case 1:
                TotalListFragment totalListFragment = new TotalListFragment();
                ToolClass.switchContent(false, getSupportFragmentManager().findFragmentByTag(oldtag),
                        totalListFragment, "1", fragmentManager);
                break;
            case 2:
                MusicHallFragment musicHallFragment = new MusicHallFragment();
                ToolClass.switchContent(false, getSupportFragmentManager().findFragmentByTag(oldtag),
                        musicHallFragment, "2", fragmentManager);
                break;
            case 3:
                SearchFragment searchFragment= new SearchFragment();
                ToolClass.switchContent(false, getSupportFragmentManager().findFragmentByTag(oldtag),
                        searchFragment, "3", fragmentManager);
                break;
            case 4:
                MoreFragment moreFragment = new MoreFragment();
                ToolClass.switchContent(false, getSupportFragmentManager().findFragmentByTag(oldtag),
                        moreFragment, "4", fragmentManager);
                break;
            case 511:
                SongListFragment songListFragment = new SongListFragment();
                ToolClass.switchContent(true, getSupportFragmentManager().findFragmentByTag(oldtag),
                        songListFragment, "511", fragmentManager);
                break;
            case 5110:
                TotalListFragment totalListFragment1 = new TotalListFragment();
                ToolClass.switchContent(false, getSupportFragmentManager().findFragmentByTag("511"),
                        totalListFragment1, "1", fragmentManager);
                break;
            case 512:
                SongListFragment songListFragment2 = new SongListFragment();
                ToolClass.switchContent(true, getSupportFragmentManager().findFragmentByTag(oldtag),
                        songListFragment2, "512", fragmentManager);
                break;
            case 5120:
                TotalListFragment totalListFragment2 = new TotalListFragment();
                ToolClass.switchContent(false, getSupportFragmentManager().findFragmentByTag("512"),
                        totalListFragment2, "1", fragmentManager);
                break;
            case 513:
                break;
            case 5130:
                break;
            case 514:
                break;
            case 5140:
                break;
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private long exitTime =   0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!sharedData.getTag().equals("511") && !sharedData.getTag().equals("512") &&
        !sharedData.getTag().equals("513") && !sharedData.getTag().equals("514")){
            if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
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
        }else{
            sharedData.setOldtag(sharedData.getTag());
            sharedData.setTag("1");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy(){
        unregisterReceiver(mainReceiver);
        stickIn.writeToSDcardFile("record.txt", "OldRain", "MainAcitivity Destroy" + "\n");
        super.onDestroy();
    }
}
