package com.example.candymusic;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        //因为安卓版本问题，在此首先建立数据库文件
        File f=this.getDatabasePath("music_list.db").getParentFile();
        if(f.exists()==false){
            f.mkdirs();//注意是mkdirs()有个s 这样可以创建多重目录。
        }
        String FullPath=f.getPath()+"/music_list.db";
        Log.d("Path", FullPath);
        //这里会输出数据库的路径 D/Path: /data/data/com.example.candymusic/databases/music_list.db
        db=SQLiteDatabase.openOrCreateDatabase(FullPath,null);
        //下面一句是如果表不存在则创建表
        db.execSQL("CREATE TABLE IF NOT EXISTS songlist(songid VARCHAR(10),songname VARCHAR(100),songdesc VARCHAR(50), songtime VARCHAR(10), songsrc VARCHAR(200));");
        //每次新打开软件实现，更新列表前删除列表，onCreate方法只执行一次
        db.execSQL("delete from songlist");


        //隐藏ActionBar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        //!!调用SongList的方法以此来写入数据库，很重要
        SongList.main();


//        老旧的创建Music文件夹方法，Android已经默认创建并提供方法
//        //检查是否有Music文件夹
//        //新建一个File，传入文件夹目录
//        String musicSrc = Environment.getExternalStorageDirectory()+"/Music";
//        File file = new File(musicSrc);
//        if(file.exists()){
//            Toast.makeText(MainActivity.this, "Music文件夹已存在，路径为"+musicSrc, Toast.LENGTH_LONG).show();
//        }else{
//            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
//            if(file.mkdirs()){
//                Toast.makeText(MainActivity.this, "已创建Music文件夹，路径为"+musicSrc, Toast.LENGTH_LONG).show();
//            }
////            file.mkdirs();
//        }
        //判断文件夹是否存在，如果不存在就创建，否则不创建
//        if (!file.exists()) {
//            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
//            file.mkdirs();
//        }



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

        //暂停播放的按钮实现
        Button playButton=(Button)findViewById(R.id.playButton);
        playButton.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlay){
                    playButton.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);

                }else{
                    playButton.setBackgroundResource(R.drawable.ic_pause_circle_black_24dp);
                }
                isPlay=!isPlay;
            }
        });


//        //隐藏ActionBar
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

    }
}