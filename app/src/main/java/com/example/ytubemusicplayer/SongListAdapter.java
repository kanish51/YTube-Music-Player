package com.example.ytubemusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>
{
    ArrayList<SongListItem> records;

    OnItemClicked activity1;
    //OnItemClickedAdd2Playlist activity2;
    public interface OnItemClicked
    {
        void SongClicked(SongListItem currSongItemObj);
    }
    //public interface OnItemClickedAdd2Playlist
    //{
    //    void AddSongToPlaylist(SongListItem currSongItemObj);
    //}

    public SongListAdapter(Context context, ArrayList<SongListItem> records)
    {
        this.records = records;
        activity1=(OnItemClicked) context;
        //activity2=(OnItemClickedAdd2Playlist) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvTitle,tvChannelName;
        ImageView ivThumbnail;
        ImageButton ibAddtoPlaylist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvChannelName=itemView.findViewById(R.id.tvChannelName);
            ivThumbnail=itemView.findViewById(R.id.ivThumbnail);
            ibAddtoPlaylist=itemView.findViewById(R.id.ibAddToPlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    //if(view.getId()==R.id.ibAddToPlaylist)
                    //{


                    //}
                    //else
                    //{
                        activity1.SongClicked(((SongListItem)view.getTag()));
                    //}

                }
            });
        }
    }

    @NonNull
    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.ViewHolder holder, int position)
    {
        holder.itemView.setTag(records.get(position));
        holder.tvTitle.setText(records.get(position).getTitle());
        holder.tvChannelName.setText(records.get(position).getChannelName());
        Glide.with(holder.itemView.getContext()).load(records.get(position).getThumbnail()).centerCrop().into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}