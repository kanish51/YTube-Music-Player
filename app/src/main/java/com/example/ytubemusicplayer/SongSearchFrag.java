package com.example.ytubemusicplayer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongSearchFrag extends Fragment {


    RecyclerView rvMain1;
    RecyclerView.LayoutManager layoutManager1;
    public SongSearchFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v1=inflater.inflate(R.layout.fragment_song_search, container, false);
        rvMain1=v1.findViewById(R.id.rvMain1);
        layoutManager1=new LinearLayoutManager(v1.getContext(),RecyclerView.VERTICAL,false);
        rvMain1.setLayoutManager(layoutManager1);
        rvMain1.setAdapter(((MainActivity)getActivity()).hehe1adapter);
        return v1;
    }

}
