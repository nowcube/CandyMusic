package com.example.candymusic;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SongList {
    public static void main() {
        File dir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_MUSIC)));
        listAllFile(dir);
    }

    public static void listAllFile(File f) {
        //声明，获取静态数据库
        SQLiteDatabase db;
        String FullPath="/data/data/com.example.candymusic/databases/music_list.db";
        Log.d("Path", FullPath);
        db= SQLiteDatabase.openDatabase(FullPath, null, SQLiteDatabase.OPEN_READWRITE);

        //android提供的媒体元信息获取的方法
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.toLowerCase().endsWith(".mp3")) {
                    String str = file.getAbsolutePath();
                    Log.d("str:",str);
                    try
                    {
                        mmr.setDataSource(str);
                        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                        Log.d("title:",title);
                        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                        Log.d("artist:",artist);
                        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
                        Log.d("duration:",duration);
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(duration));
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(duration)) - TimeUnit.MINUTES.toSeconds(minutes);
                        String songtime = String.valueOf(minutes)+"分"+String.valueOf(seconds)+"秒";
                        byte[] pic = mmr.getEmbeddedPicture();  // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图片

                        db.execSQL("INSERT INTO songlist VALUES('','"+title+"','"+artist+"','"+songtime+"','"+str+"');");

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if (file.isDirectory())
                listAllFile(file);
        }
    }
}



