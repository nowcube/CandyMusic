package com.example.candymusic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView songname,songdesc;
    ImageButton prev_btn,play_btn,next_btn;
    MediaPlayer mediaPlayer=new MediaPlayer();
    int songPos;
    int songID;
    int songCount;
    SQLiteDatabase db;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        String FullPath="/data/data/com.example.candymusic/databases/music_list.db";
        db= SQLiteDatabase.openDatabase(FullPath, null, SQLiteDatabase.OPEN_READWRITE);

        songname=(TextView) findViewById(R.id.songname);
        songdesc=(TextView) findViewById(R.id.songdesc);
        play_btn=(ImageButton)findViewById(R.id.imageButton2);
        next_btn=(ImageButton)findViewById(R.id.imageButton4);
        prev_btn=(ImageButton)findViewById(R.id.imageButton3);

        //隐藏ActionBar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        //接受从MusicListAdapter传来的数据
        Intent intent=getIntent();
        songID= Integer.parseInt(intent.getStringExtra("songID"));

        String songName=intent.getStringExtra("songName");
        String songDesc=intent.getStringExtra("songDesc");
        String songSrc=intent.getStringExtra("songSrc");

        String sql = "select * from songlist;";
        Cursor cursor = db.rawQuery(sql, null);

        //获取songCount
        Cursor mCount= db.rawQuery("select count(*) from songlist", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        songCount=count;

        songname.setText(songName);
        songdesc.setText(songDesc);

        try {
            mediaPlayer.setDataSource(songSrc);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(songPos==-1&&songID==0){
            songPos=songCount-1;
            songID=songCount;
        }else if (songPos==songCount&&songID==songCount+1){
            songPos=0;
            songID=1;
        }

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    play_btn.setImageResource(R.drawable.ic_outline_play_arrow_56);
                    mediaPlayer.pause();
                }else {
                    play_btn.setImageResource(R.drawable.ic_outline_pause_56);
                    mediaPlayer.start();
                }
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songID++;
                songPos = songID-1;
                if(songPos==-1&&songID==0){
                    songPos=songCount-1;
                    songID=songCount;
                }else if (songPos==songCount&&songID==songCount+1){
                    songPos=0;
                    songID=1;
                }
                String songNextName,songNextSrc,songNextDesc;
                cursor.moveToPosition(songPos);
                songNextName=cursor.getString(cursor.getColumnIndex("songname"));
                songNextSrc=cursor.getString(cursor.getColumnIndex("songsrc"));
                songNextDesc=cursor.getString(cursor.getColumnIndex("songdesc"));
                songname.setText(songNextName);
                songdesc.setText(songNextDesc);
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(songNextSrc);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    if (mediaPlayer.isPlaying()){
                        play_btn.setImageResource(R.drawable.ic_outline_pause_56);
                    }else {
                        play_btn.setImageResource(R.drawable.ic_outline_play_arrow_56);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songID--;
                songPos = songID-1;
                if(songPos==-1&&songID==0){
                    songPos=songCount-1;
                    songID=songCount;
                }else if (songPos==songCount&&songID==songCount+1){
                    songPos=0;
                    songID=1;
                }
                String songPrevName,songPrevSrc,songPrevDesc;
                cursor.moveToPosition(songPos);
                songPrevName=cursor.getString(cursor.getColumnIndex("songname"));
                songPrevSrc=cursor.getString(cursor.getColumnIndex("songsrc"));
                songPrevDesc=cursor.getString(cursor.getColumnIndex("songdesc"));
                songname.setText(songPrevName);
                songdesc.setText(songPrevDesc);
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(songPrevSrc);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    if (mediaPlayer.isPlaying()){
                        play_btn.setImageResource(R.drawable.ic_outline_pause_56);
                    }else {
                        play_btn.setImageResource(R.drawable.ic_outline_play_arrow_56);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    protected void onPause() {
        super.onPause();
        mediaPlayer.reset();
    }
}