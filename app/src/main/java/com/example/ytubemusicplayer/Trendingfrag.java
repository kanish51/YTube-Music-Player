package com.example.ytubemusicplayer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Trendingfrag extends Fragment
{
    RecyclerView rvMain;
    RecyclerView.LayoutManager layoutManager;
    public Trendingfrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setRetainInstance(true);
        final View v=inflater.inflate(R.layout.fragment_trendingfrag, container, false);
        Toast.makeText(v.getContext(),"Called",Toast.LENGTH_SHORT).show();
        rvMain=v.findViewById(R.id.rvMain);
        layoutManager=new LinearLayoutManager(v.getContext(),RecyclerView.VERTICAL,false);
        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(((MainActivity)getActivity()).heheadapter);
        return v;
    }
}
