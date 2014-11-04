package com.example.oldrain.player;

import com.example.oldrain.player.entity.AlbumMessage;
import com.example.oldrain.player.entity.ArtistMessage;
import com.example.oldrain.player.entity.Group;
import com.example.oldrain.player.entity.MusicMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 2014/11/2.
 * Store Music list and album and so on.which is got from the system database.
 */
public class MusicData {
    public static List<Group> artistListG = new ArrayList<Group>();
    public static List<List<ArtistMessage>> artistList = new ArrayList<List<ArtistMessage>>();
    public static List<Group> albumListG = new ArrayList<Group>();
    public static List<List<AlbumMessage>> albumList = new ArrayList<List<AlbumMessage>>();
    public static List<MusicMessage> allMusicList = new ArrayList<MusicMessage>();
}
