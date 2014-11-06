package com.example.oldrain.player.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oldrain.player.R;

/**
 * Created by Jan on 2014/11/5.
 * show lyrics
 */
public class LyricFragment extends Fragment {

    Activity home_activity;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        home_activity=activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lyric, container,
                false);

        initView(rootView);
        return rootView;
    }

    void initView(View view){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
