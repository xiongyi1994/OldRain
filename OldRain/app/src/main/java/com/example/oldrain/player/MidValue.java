package com.example.oldrain.player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jan on 2014/11/3.
 */
public class MidValue {
    public static int PlayerPosition = -1;
    public static boolean Playing = false;
    public static boolean PlayedOne = false;
    public static boolean service_run = false;

    final public static  String DB_NAME = "oldrain";

    public static int frag_tag = MidValue.SONG_TAG; //当前Fragment
    public static int frag_oldtag = MidValue.SONG_TAG; //上一个的Fragment

    //Fragment 的标志
    final public static int SONG_TAG = 1, MUSICHALL_TAG = 2, SEARCH_TAG = 3, MORE_TAG = 4,
            LOVELIST = 511, LOCALLIST = 512, DOWNLOADLIST = 513, BACK_SONGLIST = 50,
            LYRIC = 6, LYRIC_BACK = 60;

    public static ArrayList<HashMap<String, Object>> local_song;//歌曲列表

    public static int PLAY_MODEL;//播放模式的标志
    final public static int LOOP_MODEL = 1, RANDOM_MODEL = 0, ONELOOP_MODEL = 2, JUSTONE_MODEL = 3;

    final public static String ShareName = "SHARE";

    public static int FRAG_SWITCH_MODEL = MidValue.FADE_IN_OUT;
    final public static int FADE_IN_OUT = 0, UP_OUT_FADE = 1, LEFT_OUT_RIGHT_IN = 2;

    public static String Cur_SongName = "unknown", Cur_Singer = "unknown", Cur_SongPath = "unknown";

}
