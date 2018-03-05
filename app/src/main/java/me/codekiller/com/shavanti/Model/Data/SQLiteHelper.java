package me.codekiller.com.shavanti.Model.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lollipop on 2018/3/4.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper instance;

    public static SQLiteHelper getInstance(Context context){
        if (instance == null){
            instance = new SQLiteHelper(context, "local_db", null, 1);
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
        sqLiteDatabase.execSQL("create table if not exists laifudao_joke(" +
                "date text primary key," +
                "title text," +
                "content text," +
                "poster text," +
                "url text)");

        sqLiteDatabase.execSQL("create table if not exists laifudao_pic(" +
                "date text primary key," +
                "title text," +
                "thumburl text," +
                "sourceurl text," +
                "height integer," +
                "width integer," +
                "class integer," +
                "url text)");

        sqLiteDatabase.execSQL("create table if not exists juhe_news(" +
                "date text primary key," +
                "uniquekey text," +
                "title text," +
                "category text," +
                "author_name text," +
                "url text," +
                "thumbnail_pic_s text," +
                "thumbnail_pic_s02 text," +
                "thumbnail_pic_s03 text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
