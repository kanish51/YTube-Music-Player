package com.example.ytubemusicplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SongListAdapter.OnItemClicked
{

    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    ArrayList<SongListItem> songs;
    ArrayList<SongListItem> searchsongs;
    String apiUrl,apiUrl1,apiUrl2;
    RecyclerView.Adapter heheadapter;
    RecyclerView.Adapter hehe1adapter;
    RequestQueue r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        searchView=findViewById(R.id.searchView);
        searchView.setQueryHint("Search songs!");
        songs=new ArrayList<SongListItem>();
        searchsongs=new ArrayList<SongListItem>();
        heheadapter=new SongListAdapter(this,songs);
        hehe1adapter=new SongListAdapter(this,searchsongs);
        apiUrl="https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=30&regionCode=IN&videoCategoryId=10&key=AIzaSyBE_ZpdeQIs4LNfCCR24gtsw9jFvXiMhfY";
        apiUrl1="https://www.googleapis.com/youtube/v3/search?part=snippet&eventType=completed&maxResults=20&order=relevance&q=";
        apiUrl2="&regionCode=IN&type=video&key=AIzaSyBE_ZpdeQIs4LNfCCR24gtsw9jFvXiMhfY";
         r= Volley.newRequestQueue(this);
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
                    heheadapter.notifyDataSetChanged();
                    Log.d("Size of SONGLIST",songs.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                    ProblemFunc();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                ProblemFunc();
            }
        });
        r.add(j);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Trendingfrag(),"Trending");
        viewPagerAdapter.addFragment(new Recentfrag(),"Recents");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                if (viewPagerAdapter.getCount() != 3)
                {
                    viewPagerAdapter.addFragment(new SongSearchFrag(),"Search");
                    viewPagerAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(2,false);
                }
                JsonObjectRequest j1 = new JsonObjectRequest(Request.Method.GET,apiUrl1+s+apiUrl2,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray itemList=response.getJSONArray("items");
                            JSONObject obj,obj1;
                            String title,thumbNail,channelName,link;
                            searchsongs.clear();
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
                            hehe1adapter.notifyDataSetChanged();
                            Log.d("Size of search SONGLIST",songs.size()+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                            ProblemFunc();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        ProblemFunc();
                    }
                });
                r.add(j1);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s)
            {
                return false;
            }
        });
    }
    public void ProblemFunc()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Alert!");
        alert.setMessage("Something went wrong! Try again later...");
        alert.setCancelable(false);
        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                MainActivity.this.finish();
                System.exit(0);
            }
        });
        alert.create().show();
    }
    @Override
    public void onBackPressed()
    {
        if (viewPagerAdapter.getCount() == 3)
        {
            viewPager.setCurrentItem(0,false);
            viewPagerAdapter.removeFragment(2);
            viewPagerAdapter.notifyDataSetChanged();
            tabLayout.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void SongClicked(String vidId)
    {
        Toast.makeText(this,"hey"+vidId,Toast.LENGTH_SHORT).show();
    }
}
//https://www.googleapis.com/youtube/v3/search?part=snippet&eventType=completed&order=relevance&q=lehanga&regionCode=IN&type=video&key=AIzaSyBE_ZpdeQIs4LNfCCR24gtsw9jFvXiMhfY