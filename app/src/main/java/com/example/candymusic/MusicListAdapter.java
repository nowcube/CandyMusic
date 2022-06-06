package com.example.candymusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>{
    private List<Song> songList;

    public MusicListAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_song_item,parent,false);
        MusicListViewHolder musicListViewHolder = new MusicListViewHolder(itemView);
        return musicListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.nameText.setText(song.getSongName());
        holder.descText.setText(song.getSongDesc());
        holder.timeText.setText(song.getSongTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcText=(song.getSongSrc());
                Toast.makeText(v.getContext(),srcText,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class MusicListViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        TextView descText;
        TextView timeText;

        public MusicListViewHolder(View itemView) {
            super(itemView);
            this.nameText = itemView.findViewById(R.id.songTitle);
            this.descText = itemView.findViewById(R.id.songDesc);
            this.timeText = itemView.findViewById(R.id.songTime);
        }
    }
}
