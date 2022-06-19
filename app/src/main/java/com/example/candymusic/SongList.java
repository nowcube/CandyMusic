package com.example.candymusic;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SongList {
    String FullPath="/data/data/com.example.candymusic/databases/music_list.db";
    SQLiteDatabase db= SQLiteDatabase.openDatabase(FullPath, null, SQLiteDatabase.OPEN_READWRITE);

    public void main() {
        File dir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_MUSIC)));
        listAllFile(dir);
    }
    public void listAllFile(File f) {
        //android提供的媒体元信息获取的方法
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.toLowerCase().endsWith(".mp3") || fileName.toLowerCase().endsWith(".flac") || fileName.toLowerCase().endsWith(".acc")) {
                    String str = file.getAbsolutePath();
                    try{
                        mmr.setDataSource(str);
                        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(duration));
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(duration)) - TimeUnit.MINUTES.toSeconds(minutes);
                        String songtime = String.valueOf(minutes)+"分"+String.valueOf(seconds)+"秒";
                        byte[] pic = mmr.getEmbeddedPicture();  // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图片
//                        String uniqueID = UUID.randomUUID().toString();//为每一首歌曲生成唯一UUID
                        db.execSQL("INSERT INTO songlist VALUES(NULL,'"+title+"','"+artist+"','"+songtime+"','"+str+"');");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (file.isDirectory()){listAllFile(file);}
        }
    }
}



