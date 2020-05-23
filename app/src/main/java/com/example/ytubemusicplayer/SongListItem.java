package com.example.ytubemusicplayer;

public class SongListItem
{
    public String link=null;
    public String title=null;
    public String channelName=null;
    public String thumbnail=null;

    public SongListItem(String link, String title, String channelName, String thumbnail) {
        this.link = link;
        this.title = title;
        this.channelName = channelName;
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}