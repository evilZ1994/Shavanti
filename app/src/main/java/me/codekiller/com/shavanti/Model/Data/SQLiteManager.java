package me.codekiller.com.shavanti.Model.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;

/**
 * Created by Lollipop on 2018/3/4.
 */

public class SQLiteManager {
    private static SQLiteManager instance;
    private Context context;
    private SQLiteDatabase database;

    public static SQLiteManager getInstance(Context context){
        if (instance == null){
            instance = new SQLiteManager(context);
        }

        return instance;
    }

    public SQLiteManager(Context context){
        this.context = context;
        database = SQLiteHelper.getWritableDatabase(context);
    }

    /**
     * 将joke保存到本地数据库
     * @param consumer
     * @param joke
     */
    public void addLaifudaoJoke(Consumer<String> consumer, final LaifudaoJoke joke){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("date", joke.getDate());
                values.put("title", joke.getTitle());
                values.put("content", joke.getContent());
                values.put("poster", joke.getPoster());
                values.put("url", joke.getUrl());
                database.insert("laifudao_joke", null, values);
                values.clear();
                database.setTransactionSuccessful();
                database.endTransaction();

                emitter.onNext("done");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 将搞笑图片保存到本地数据库
     * @param consumer
     * @param pic
     */
    public void addLaifudaoPic(Consumer<String> consumer, final LaifudaoPic pic){
        Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("date", pic.getDate());
                values.put("title", pic.getTitle());
                values.put("thumburl", pic.getThumburl());
                values.put("sourceurl", pic.getSourceurl());
                values.put("height", pic.getHeight());
                values.put("width", pic.getWidth());
                values.put("class", pic.getClassX());
                values.put("url", pic.getUrl());
                database.insert("laifudao_pic", null, values);
                values.clear();
                database.setTransactionSuccessful();
                database.endTransaction();

                emitter.onNext("done");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 将聚合数据新闻头条保存到本地数据库
     * @param consumer
     * @param news
     */
    public void addJuheNews(Consumer<String> consumer, final JuheNews.ResultBean.DataBean news){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("date", news.getDate());
                values.put("uniquekey", news.getUniquekey());
                values.put("title", news.getTitle());
                values.put("category", news.getCategory());
                values.put("author_name", news.getAuthor_name());
                values.put("url", news.getUrl());
                values.put("thumbnail_pic_s", news.getThumbnail_pic_s());
                values.put("thumbnail_pic_s02", news.getThumbnail_pic_s02());
                values.put("thumbnail_pic_s03", news.getThumbnail_pic_s03());
                database.insert("juhe_news", null, values);
                values.clear();
                database.setTransactionSuccessful();
                database.endTransaction();

                emitter.onNext("done");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
