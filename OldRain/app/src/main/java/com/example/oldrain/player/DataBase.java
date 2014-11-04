package com.example.oldrain.player;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataBase {
    private SQLiteDatabase db;
    public static String DBNAME = Environment.getExternalStorageDirectory().toString() + "/OldRain/oldrain.db";
    public DataBase(Context context) {
        db = context.openOrCreateDatabase(DBNAME,Context.MODE_PRIVATE, null);
    }

    public void saveData(HashMap<String, Object> listItem) {
        String db_name = MidValue.DB_NAME;
        db.execSQL("CREATE table IF NOT EXISTS "+ db_name
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,playtag VARCHAR(300)," +
                "name VARCHAR(300), singer VARCHAR(300), album VARCHAR(300), path VARCHAR(300), lovetag VARCHAR(300))");

        db.execSQL("insert into "+db_name+"  values(NULL,?,?,?,?,?,?)",
                new Object[] {listItem.get("playtag").toString(), listItem.get("name").toString(),
                        listItem.get("singer").toString(), listItem.get("album").toString(),
                        listItem.get("path").toString(), listItem.get("lovetag").toString()});
    }

    public void updataData(String tag, String change, String path){
        String db_name = MidValue.DB_NAME;
        db.execSQL("CREATE table IF NOT EXISTS "+ db_name
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,playtag VARCHAR(300)," +
                "name VARCHAR(300), singer VARCHAR(300), album VARCHAR(300), path VARCHAR(300), lovetag VARCHAR(300))");
        if(change.equals("playtag")){
            db.execSQL("UPDATE "+db_name+" SET " +
                    "playtag = " + tag +
                    " WHERE path = \"" + path + "\"");
        }else if(change.equals("lovetag")){
            db.execSQL("UPDATE "+db_name+" SET " +
                    "lovetag = \"" + tag + "\"" +
                    " WHERE path = \"" + path + "\"");
        }
    }
    public void deleteData(String name, String path){
        String db_name = MidValue.DB_NAME;
        db.execSQL("CREATE table IF NOT EXISTS "+ db_name
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,playtag VARCHAR(300)," +
                "name VARCHAR(300), singer VARCHAR(300), album VARCHAR(300), path VARCHAR(300), lovetag VARCHAR(300))");
        //db.delete("ALLMSG", )
        db.execSQL("delete from "+db_name+"  WHERE name = \"" + name + "\"  AND path = \"" + path + "\"");
    }
    public ArrayList<HashMap<String, Object>> getData() {
        String db_name = MidValue.DB_NAME;
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        db.execSQL("CREATE table IF NOT EXISTS "+ db_name
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,playtag VARCHAR(300)," +
                "name VARCHAR(300), singer VARCHAR(300), album VARCHAR(300), path VARCHAR(300), lovetag VARCHAR(300))");
        Cursor c = db.rawQuery("SELECT * from "+db_name + " ORDER BY _id DESC LIMIT 999", null);
        while (c.moveToNext()) {
            String playtag = c.getString(c.getColumnIndex("playtag"));
            String name = c.getString(c.getColumnIndex("name"));
            String singer = c.getString(c.getColumnIndex("singer"));
            String path = c.getString(c.getColumnIndex("path"));
            String album = c.getString(c.getColumnIndex("album"));
            String lovetag = c.getString(c.getColumnIndex("lovetag"));

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("playtag", playtag);
            map.put("name", name);
            map.put("singer", singer);
            map.put("path", path);
            map.put("album", album);
            map.put("lovetag", lovetag);
            listItem.add(map);
        }
        c.close();
        Collections.reverse(listItem);
        return listItem;
    }

    public void close() {
        if (db != null)
            db.close();
    }
}
