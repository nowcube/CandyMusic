package com.example.candymusic;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    //布尔函数判断当前状态
    static boolean isPlay = false;
    //生命数据库
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏ActionBar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        //因为安卓版本问题，在此首先建立数据库文件
        File f=this.getDatabasePath("music_list.db").getParentFile();
        if(f.exists()==false){
            f.mkdirs();//注意是mkdirs()有个s 这样可以创建多重目录。
        }
        String FullPath=f.getPath()+"/music_list.db";
//        Log.d("Path", FullPath);
        //这里会输出数据库的路径 D/Path: /data/data/com.example.candymusic/databases/music_list.db
        db=SQLiteDatabase.openOrCreateDatabase(FullPath,null);
        //如果表存在则删除
        db.execSQL("drop table if exists songlist;");
        //下面一句是如果表不存在则创建表
        db.execSQL("CREATE TABLE IF NOT EXISTS songlist(songid INTEGER PRIMARY KEY AUTOINCREMENT,songname VARCHAR(100),songdesc VARCHAR(50), songtime VARCHAR(10), songsrc VARCHAR(200));");
        //每次新打开软件实现，更新列表前删除列表，onCreate方法只执行一次
        db.execSQL("delete from songlist");

        SongList songList = new SongList();
        songList.main();

        //点按转到MusicListActivity
        findViewById(R.id.musicList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MusicListActivity.class);
                startActivity(intent);
            }
        });

        //测试关于的Toast
        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
            }
        });


    }
}