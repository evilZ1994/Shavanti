package me.codekiller.com.shavanti.Model.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lollipop on 2018/3/4.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper instance;
    private static final String DATABASE_NAME = "local_db";
    private static final int FIRST_VERSION = 1;
    private static final int CURRENT_VERSION = 2;

    public static SQLiteHelper getInstance(Context context){
        if (instance == null){
            instance = new SQLiteHelper(context, DATABASE_NAME, null, CURRENT_VERSION);
        }

        return instance;
    }

    public static SQLiteDatabase getWritableDatabase(Context context){
        return getInstance(context).getWritableDatabase();
    }

    public static SQLiteDatabase getReadableDatabase(Context context){
        return getInstance(context).getReadableDatabase();
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //存储日期，用于首页列表分组
        sqLiteDatabase.execSQL("create table if not exists key_date(" +
                "keyDate text not null)");

        sqLiteDatabase.execSQL("create table if not exists laifudao_joke(" +
                "keyDate text not null," +
                "title text," +
                "content text," +
                "poster text," +
                "url text not null primary key)");

        sqLiteDatabase.execSQL("create table if not exists laifudao_pic(" +
                "keyDate text not null," +
                "title text," +
                "thumburl text," +
                "sourceurl text," +
                "height integer," +
                "width integer," +
                "class integer," +
                "url text not null primary key)");

        sqLiteDatabase.execSQL("create table if not exists juhe_news(" +
                "keyDate text not null," +
                "date text," +
                "uniquekey text," +
                "title text," +
                "category text," +
                "author_name text," +
                "url text not null primary key," +
                "thumbnail_pic_s text," +
                "thumbnail_pic_s02 text," +
                "thumbnail_pic_s03 text)");

        sqLiteDatabase.execSQL("create table if not exists one_pic(" +
                "keyDate text not null," +
                "url text not null primary key," +
                "imgUrl text," +
                "description text)");

        sqLiteDatabase.execSQL("create table if not exists one_article(" +
                "keyDate text not null," +
                "title text," +
                "description text," +
                "article text," +
                "author text," +
                "url text not null primary key)");

        onUpgrade(sqLiteDatabase, FIRST_VERSION, CURRENT_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1){
            sqLiteDatabase.execSQL("create table if not exists juhe_news_cache(" +
                    "date text," +
                    "uniquekey text," +
                    "title text," +
                    "category text," +
                    "author_name text," +
                    "url text not null primary key," +
                    "thumbnail_pic_s text," +
                    "thumbnail_pic_s02 text," +
                    "thumbnail_pic_s03 text)");

            sqLiteDatabase.execSQL("create table if not exists juhe_news_bookmark(" +
                    "date text," +
                    "uniquekey text," +
                    "title text," +
                    "category text," +
                    "author_name text," +
                    "url text not null primary key," +
                    "thumbnail_pic_s text," +
                    "thumbnail_pic_s02 text," +
                    "thumbnail_pic_s03 text)");
        }

        if (newVersion-oldVersion > 1){
            onUpgrade(sqLiteDatabase, oldVersion+1, newVersion);
        }
    }
}
