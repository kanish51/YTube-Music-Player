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
    RecyclerView.Adapter adapter,adapter2;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SongListItem> songs;
    ArrayList<SongListItem> searchsongs;
    RequestQueue r;
    String apiUrl,apiUrl1,apiUrl2;
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
        songs=new ArrayList<SongListItem>();
        searchsongs=new ArrayList<SongListItem>();
        adapter=new SongListAdapter(getActivity(),songs);
        adapter2=new SongListAdapter(getActivity(),searchsongs);
        rvMain.setAdapter(adapter);
        apiUrl="https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=30&regionCode=IN&videoCategoryId=10&key=AIzaSyBE_ZpdeQIs4LNfCCR24gtsw9jFvXiMhfY";
        apiUrl1="https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&order=relevance&q=";
        apiUrl2="&regionCode=IN&key=AIzaSyBE_ZpdeQIs4LNfCCR24gtsw9jFvXiMhfY";
        r= Volley.newRequestQueue(v.getContext());
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET,apiUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray itemList=response.getJSONArray("items");
                    JSONObject obj,obj1;
                    String title,thumbNail,channelName,link;
                    for(int i=0;i<itemList.length();i++)
                    {
                        obj=itemList.getJSONObject(i);
                        obj1=obj.getJSONObject("snippet");
                        link=obj.getString("id");
                        title=obj1.getString("title");
                        thumbNail=obj1.getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                        channelName=obj1.getString("channelTitle");
                        songs.add(new SongListItem(link,title,channelName,thumbNail));
                    }
                    Log.d("Size of SONGLIST",songs.size()+"");
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(v.getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                    ((MainActivity)getActivity()).ProblemFunc();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(v.getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).ProblemFunc();
            }
        });
        r.add(j);
        return v;
    }
    public void ShowSearchResults(String query)
    {
        rvMain.setAdapter(adapter2);
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET,apiUrl1+query+apiUrl2,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    searchsongs.clear();
                    JSONArray itemList=response.getJSONArray("items");
                    JSONObject obj,obj1;
                    String title,thumbNail,channelName,link;
                    for(int i=0;i<itemList.length();i++)
                    {
                        obj=itemList.getJSONObject(i);
                        obj1=obj.getJSONObject("snippet");
                        link=obj.getString("id");
                        title=obj1.getString("title");
                        thumbNail=obj1.getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                        channelName=obj1.getString("channelTitle");
                        searchsongs.add(new SongListItem(link,title,channelName,thumbNail));
                    }
                    Log.d("Size of Search SONGLIST",searchsongs.size()+"");
                    adapter2.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(v.getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                    ((MainActivity)getActivity()).ProblemFunc();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(v.getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).ProblemFunc();
            }
        });
        r.add(j);
    }
    public void RevertTrendingScreen()
    {
        rvMain.setAdapter(adapter);
    }
}
