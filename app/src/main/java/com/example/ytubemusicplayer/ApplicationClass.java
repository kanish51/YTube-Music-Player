package com.example.ytubemusicplayer;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public class ApplicationClass extends Application
{
    public static int lastIndex=-1;
    public static ArrayList<SongListItem> currPlayingSongs=new ArrayList<SongListItem>();
    public static void AddSong(SongListItem item)
    {
        currPlayingSongs.add(item);
        lastIndex++;
    }
    public static void RemoveSong(SongListItem item)
    {
        currPlayingSongs.remove(lastIndex);
        lastIndex--;
    }
    public static MediaDescriptionCompat getMediaDescription(Context context, SongListItem item) {
        Bundle extras = new Bundle();
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, item.getBitThumbnail());
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, item.getBitThumbnail());
        return new MediaDescriptionCompat.Builder()
                .setIconBitmap(item.getBitThumbnail())
                .setTitle(item.getTitle())
                .setDescription(item.getChannelName())
                .setExtras(extras)
                .build();
    }

}
