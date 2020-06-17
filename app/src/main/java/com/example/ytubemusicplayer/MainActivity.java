package com.example.ytubemusicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity implements SongListAdapter.OnItemClicked
{

    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    SlidingUpPanelLayout mLayout;
    ProgressDialog progressDialog;
    SongListItem tempObj;
    //Bitmap icon;
    String songurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //songurl="https://r4---sn-oxu2a0npo-a0ie.googlevideo.com/videoplayback?expire=1590629933&ei=zcHOXpmiI9G3gwPehLXACA&ip=54.165.127.187&id=o-AAg-2U3ucBdXsb7adq_zXcwS8tidSr0QNKuX5JP5ZbEx&itag=251&source=youtube&requiressl=yes&vprv=1&mime=audio%2Fwebm&gir=yes&clen=3971637&dur=225.941&lmt=1574658948235857&fvip=4&keepalive=yes&c=WEB&txp=5531432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAI6hf3ogzdSopCIzVPeZeFB7_Wp-boYLsa_of8bqaIQjAiBOimHa_pZuJqXjoYJkIRblOcPbOMg6NW1QHj-B1hWYFg==&ratebypass=yes&redirect_counter=1&rm=sn-p5qkz7z&req_id=871725ae86ba3ee&cms_redirect=yes&ipbypass=yes&mh=ni&mip=103.41.24.114&mm=31&mn=sn-oxu2a0npo-a0ie&ms=au&mt=1590608249&mv=m&mvi=3&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAN80zRHgSvO_F5BrgQJI8Vo1CAP_Ni76MrRJe0wjhCHHAiEAhXsxlqb8V90Lnu3jnj69t9TxgzfW-02IRouMEO8PGMk%3D";
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        mLayout = (SlidingUpPanelLayout)findViewById(R.id.activity_main);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        viewPager.setOffscreenPageLimit(1);
        searchView=findViewById(R.id.searchView);
        searchView.setQueryHint("Search songs!");
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Trendingfrag(),"Trending");
        viewPagerAdapter.addFragment(new Recentfrag(),"Recents");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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
    public void SongClicked(SongListItem currSongItemObj)
    {
        Toast.makeText(this,"hey"+currSongItemObj.getId(),Toast.LENGTH_SHORT).show();
        tempObj=currSongItemObj;
        //currSongItemObj.setUrl(Uri.parse("https://myappserver51.herokuapp.com/api/play?url=https://www.youtube.com/watch?v="+currSongItemObj.getId()+"&format=bestaudio"));
        //Bitmap theBitmap = Glide.with(this).asBitmap().load("http://....").into(320, 180).get();
        /*Glide.with(this).asBitmap().load(currSongItemObj.getThumbnail()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                icon=resource;
            }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });*/

        RetrieveRedirectURL async=new RetrieveRedirectURL();
        async.execute("https://myappserver51.herokuapp.com/api/play?url=https://www.youtube.com/watch?v="+currSongItemObj.getId()+"&format=bestaudio");

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
    public class RetrieveRedirectURL extends AsyncTask<String,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(String... strings)
        {
            URLConnection con = null;
            try {
                con = new URL(strings[0]).openConnection();
                con.connect();
                InputStream is = con.getInputStream();
                songurl=con.getURL().toString();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tempObj.setUrl(Uri.parse(songurl));
            tempObj.setBitThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.album_art_1));
            ApplicationClass.AddSong(tempObj);
            //ApplicationClass.currPlayingSongs.add(tempObj);
            progressDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, AudioPlayerService.class);
            ContextCompat.startForegroundService(MainActivity.this,intent);

        }
    }
}
