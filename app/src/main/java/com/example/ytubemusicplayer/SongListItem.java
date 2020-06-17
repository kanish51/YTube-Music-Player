package com.example.ytubemusicplayer;

import android.graphics.Bitmap;
import android.net.Uri;

public class SongListItem
{
    private Uri url=null;
    private String id=null;
    private String title=null;
    private String channelName=null;
    private String thumbnail=null;
    private Bitmap bitThumbnail=null;

    public SongListItem(Uri url,String id, String title, String channelName,String thumbnail, Bitmap bitThumbnail) {
        this.url = url;
        this.id=id;
        this.title = title;
        this.channelName = channelName;
        this.thumbnail = thumbnail;
        this.bitThumbnail=bitThumbnail;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Bitmap getBitThumbnail() {
        return bitThumbnail;
    }

    public void setBitThumbnail(Bitmap bitThumbnail) {
        this.bitThumbnail = bitThumbnail;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}