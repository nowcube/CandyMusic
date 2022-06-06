package com.example.candymusic;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //引入音乐数据库，由MainActivity创建
        SQLiteDatabase db;
        String FullPath="/data/data/com.example.candymusic/databases/music_list.db";
        Log.d("Path", FullPath);
        db= SQLiteDatabase.openDatabase(FullPath, null, SQLiteDatabase.OPEN_READWRITE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        //隐藏ActionBar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
//            actionBar.hide();
            actionBar.setTitle("歌曲列表");
        }

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        //实现遍历数据库
        String sql = "select * from songlist;";
        List<Song> songList = new ArrayList<>();
        Song song = null;
        Cursor cursor = db.rawQuery(sql, null);
        //遍历数据库
        while (cursor.moveToNext()) {
            song = new Song();
            song.setSongName(cursor.getString(cursor
                    .getColumnIndex("songname")));
            song.setSongDesc(cursor.getString(cursor
                    .getColumnIndex("songdesc")));
            song.setSongTime(cursor.getString(cursor
                    .getColumnIndex("songtime")));
            song.setSongSrc(cursor.getString(cursor
                    .getColumnIndex("songsrc")));
            songList.add(song);
        }


//        //老旧的静态创立方法
//        List<Song> songList = new ArrayList<>();
//        Song song1= new Song("GoodTime","初音ミク","1m30s");
//        songList.add(song1);

        //给RecyclerView设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //给RecyclerView设置适配器
        MusicListAdapter musicListAdapter = new MusicListAdapter(songList);
        recyclerView.setAdapter(musicListAdapter);

    }

}
