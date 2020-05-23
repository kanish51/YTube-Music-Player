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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        searchView=findViewById(R.id.searchView);
        searchView.setQueryHint("Search songs!");
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
                viewPager.setCurrentItem(0,false);
                tabLayout.setVisibility(View.GONE);
                Trendingfrag fragment1 = (Trendingfrag) viewPagerAdapter.getItem(0);
                fragment1.ShowSearchResults(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                return false;
            }
        });
    }
    @Override
    public void SongClicked(String vidId)
    {
        Toast.makeText(this,"hey"+vidId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        if (viewPager.getCurrentItem() == 0)
        {
            if(tabLayout.getVisibility()== View.GONE)
            {
                Trendingfrag fragment2 = (Trendingfrag) viewPagerAdapter.getItem(0);
                fragment2.RevertTrendingScreen();
                searchView.setIconified(true);
                searchView.clearFocus();
                tabLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                super.onBackPressed();
            }
        }
        else {
            super.onBackPressed();
        }
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
}
