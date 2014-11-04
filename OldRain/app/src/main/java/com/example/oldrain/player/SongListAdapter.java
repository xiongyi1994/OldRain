package com.example.oldrain.player;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 14-7-23.
 */
class ViewHolder {
    //public RelativeLayout item_layout;
    public RelativeLayout items;
    public ImageButton favor_tag;
    public TextView song_name;
    public TextView singer;
    public Button play_tag;
}

public class SongListAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private Context contexts;
    private Activity activity;
    private ArrayList<HashMap<String, Object>> item_contents;
    private int layout_ids;
    private int[] item_ids;
    public SongListAdapter(Context context){
        super();
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contexts = context;
    }

    public SongListAdapter(Activity context, ArrayList<HashMap<String, Object>> item_content, int layout_id, int[] item_id){
        super();
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contexts = context;
        activity = context;
        item_contents = item_content;
        layout_ids = layout_id;
        item_ids = item_id;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return item_contents.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(layout_ids, null);

            holder.play_tag = (Button) convertView.findViewById(item_ids[0]);
            holder.song_name = (TextView) convertView.findViewById(item_ids[1]);
            holder.singer = (TextView) convertView.findViewById(item_ids[2]);
            holder.favor_tag = (ImageButton) convertView.findViewById(item_ids[3]);
            holder.items = (RelativeLayout) convertView.findViewById(R.id.song_list_items);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.singer.setText(item_contents.get(position).get("singer").toString());
        holder.song_name.setText(item_contents.get(position).get("name").toString());
        if(item_contents.get(position).get("playtag").equals("0")){  //0：未播放；1：播放中；2：未拥有
            holder.play_tag.setBackgroundColor(contexts.getResources().getColor(R.color.transparent));
        }else if(item_contents.get(position).get("playtag").equals("1")){
            holder.play_tag.setBackgroundColor(contexts.getResources().getColor(R.color.loved));
        }else{
            holder.play_tag.setBackgroundColor(contexts.getResources().getColor(R.color.download_false));
        }
        if(item_contents.get(position).get("lovetag").equals("1")){
            holder.favor_tag.setImageDrawable(contexts.getResources().getDrawable(R.drawable.favor_true));
        }else{
            holder.favor_tag.setImageDrawable(contexts.getResources().getDrawable(R.drawable.favor_false));
        }
        holder.favor_tag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DataBase dataBase = new DataBase(contexts);
                String lovetag = item_contents.get(position).get("lovetag").toString();
                HashMap<String, Object> song = new HashMap<String, Object>();
                song.put("name", item_contents.get(position).get("name"));
                song.put("singer", item_contents.get(position).get("singer"));
                song.put("playtag", item_contents.get(position).get("playtag"));
                song.put("lovetag", lovetag.equals("0")?"1":"0");
                song.put("path", item_contents.get(position).get("path"));
                song.put("album", item_contents.get(position).get("album"));
                item_contents.set(position, song);
                dataBase.updataData(lovetag.equals("0")?"1":"0", "lovetag",
                        item_contents.get(position).get("path").toString());

                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
