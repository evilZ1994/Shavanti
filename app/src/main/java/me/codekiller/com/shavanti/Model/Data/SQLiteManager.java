package me.codekiller.com.shavanti.Model.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.DateTitle;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.Model.Bean.OneArticle;
import me.codekiller.com.shavanti.Model.Bean.OnePic;
import me.codekiller.com.shavanti.Utils.DateUtil;

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
     * 保存作为分组依据的日期
     */
    public void storeKeyDate(){
        database.beginTransaction();
        String keyDate = DateUtil.dateFormat(new Date(), context);
        ContentValues values = new ContentValues();
        values.put("keyDate", keyDate);
        database.insert("key_date", null, values);
        values.clear();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**
     * 将joke保存到本地数据库
     * @param consumer
     * @param jokes
     */
    public void addLaifudaoJokes(Consumer<String> consumer, final List<LaifudaoJoke> jokes){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                for (LaifudaoJoke joke : jokes) {
                    ContentValues values = new ContentValues();
                    values.put("keyDate", joke.getKeyDate());
                    values.put("title", joke.getTitle());
                    values.put("content", joke.getContent());
                    values.put("poster", joke.getPoster());
                    values.put("url", joke.getUrl());
                    database.insert("laifudao_joke", null, values);
                    values.clear();
                }
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
     * @param pics
     */
    public void addLaifudaoPic(Consumer<String> consumer, final List<LaifudaoPic> pics){
        Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                for (LaifudaoPic pic : pics) {
                    ContentValues values = new ContentValues();
                    values.put("keyDate", pic.getKeyDate());
                    values.put("title", pic.getTitle());
                    values.put("thumburl", pic.getThumburl());
                    values.put("sourceurl", pic.getSourceurl());
                    values.put("height", pic.getHeight());
                    values.put("width", pic.getWidth());
                    values.put("class", pic.getClassX());
                    values.put("url", pic.getUrl());
                    database.insert("laifudao_pic", null, values);
                    values.clear();
                }
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
     * @param topNews
     */
    public void addJuheNews(Consumer<String> consumer, final List<JuheNews.ResultBean.DataBean> topNews){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                for (JuheNews.ResultBean.DataBean news : topNews) {
                    ContentValues values = new ContentValues();
                    values.put("keyDate", news.getKeyDate());
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
                }
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
     * 保存One一个的图片和文字到本地
     * @param consumer
     * @param onePic
     */
    public void addOnePic(Consumer<String> consumer, final OnePic onePic){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("keyDate", onePic.getKeyDate());
                values.put("url", onePic.getUrl());
                values.put("imgUrl", onePic.getImgUrl());
                values.put("description", onePic.getDescription());
                database.insert("one_pic", null, values);
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
     * 将One一个的文章保存到本地
     * @param consumer
     * @param article
     */
    public void addOneArticle(Consumer<String> consumer, final OneArticle article){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                database.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("keyDate", article.getKeyDate());
                values.put("title", article.getTitle());
                values.put("description", article.getDescription());
                values.put("article", article.getArticle());
                values.put("url", article.getUrl());
                values.put("author", article.getAuthor());
                database.insert("one_article", null, values);
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
     * 查询本地数据
     * @param consumer
     */
    public void loadLocal(Consumer<List<BaseBean>> consumer){
        Observable.create(new ObservableOnSubscribe<List<BaseBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BaseBean>> emitter) throws Exception {
                List<BaseBean> beans = new ArrayList<>();
                Cursor cursor = database.query("key_date", null, null, null, null, null, null);
                //从后往前添加，让最新日期显示在最前
                if (cursor.moveToLast()){
                    do {
                        String keyDate = cursor.getString(cursor.getColumnIndex("keyDate"));
                        DateTitle dateTitle = new DateTitle();
                        dateTitle.setKeyDate(keyDate);
                        beans.add(dateTitle);
                        beans.add(selectJokeByDate(keyDate));
                        beans.add(selectFunnyPicByDate(keyDate));
                        beans.add(selectJuheNewsByDate(keyDate));
                        beans.add(selectOnePicByDate(keyDate));
                        beans.add(selectOneArticleByDate(keyDate));
                    }while (cursor.moveToPrevious());
                }
                cursor.close();
                //清除list中的空元素
                beans.removeAll(Collections.singleton(null));
                emitter.onNext(beans);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 查询首页joke
     * @param date
     * @return
     */
    public LaifudaoJoke selectJokeByDate(String date){
        Cursor cursor = database.query("laifudao_joke", null, "keyDate=?", new String[]{date}, null, null, null);
        LaifudaoJoke joke;
        if (cursor.moveToFirst()){
            joke = new LaifudaoJoke();
            joke.setKeyDate(date);
            joke.setContent(cursor.getString(cursor.getColumnIndex("content")));
            joke.setPoster(cursor.getString(cursor.getColumnIndex("poster")));
            joke.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            joke.setUrl(cursor.getString(cursor.getColumnIndex("url")));

            cursor.close();
            return joke;
        }

        return null;
    }

    /**
     * 查询首页funny pic
     * @param date
     * @return
     */
    public LaifudaoPic selectFunnyPicByDate(String date){
        Cursor cursor = database.query("laifudao_pic", null, "keyDate=?", new String[]{date}, null, null, null);
        LaifudaoPic pic;
        if (cursor.moveToFirst()){
            pic = new LaifudaoPic();
            pic.setKeyDate(date);
            pic.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            pic.setClassX(cursor.getString(cursor.getColumnIndex("class")));
            pic.setThumburl(cursor.getString(cursor.getColumnIndex("thumburl")));
            pic.setSourceurl(cursor.getString(cursor.getColumnIndex("sourceurl")));
            pic.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            pic.setHeight(cursor.getInt(cursor.getColumnIndex("height")));
            pic.setWidth(cursor.getInt(cursor.getColumnIndex("width")));

            cursor.close();
            return pic;
        }

        return null;
    }

    /**
     * 查询首页聚合新闻头条
     * @param date
     * @return
     */
    public JuheNews.ResultBean.DataBean selectJuheNewsByDate(String date){
        Cursor cursor = database.query("juhe_news", null, "keyDate=?", new String[]{date}, null, null, null);
        JuheNews.ResultBean.DataBean news;
        if (cursor.moveToFirst()){
            news = new JuheNews.ResultBean.DataBean();
            news.setKeyDate(date);
            news.setDate(cursor.getString(cursor.getColumnIndex("date")));
            news.setUniquekey(cursor.getString(cursor.getColumnIndex("uniquekey")));
            news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            news.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
            news.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            news.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s")));
            news.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02")));
            news.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03")));
            news.setUrl(cursor.getString(cursor.getColumnIndex("url")));

            cursor.close();
            return news;
        }

        return null;
    }

    /**
     * 查询首页One一个的图片和文字
     * @param date
     * @return
     */
    public OnePic selectOnePicByDate(String date){
        Cursor cursor = database.query("one_pic", null, "keyDate=?", new String[]{date}, null, null, null);
        OnePic onePic = null;
        if (cursor.moveToFirst()){
            onePic = new OnePic();
            onePic.setKeyDate(date);
            onePic.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            onePic.setImgUrl(cursor.getString(cursor.getColumnIndex("imgUrl")));
            onePic.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            cursor.close();
        }
        return onePic;
    }

    /**
     * 查询首页One一个的文章
     * @param date
     * @return
     */
    public OneArticle selectOneArticleByDate(String date){
        Cursor cursor = database.query("one_article", null, "keyDate=?", new String[]{date}, null, null, null);
        OneArticle article = null;
        if (cursor.moveToFirst()){
            article = new OneArticle();
            article.setKeyDate(date);
            article.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            article.setArticle(cursor.getString(cursor.getColumnIndex("article")));
            article.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            article.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            cursor.close();
        }
        return article;
    }

    /**
     * 查询某天所有的joke
     * @param observer
     * @param keyDate
     */
    public void selectAllJokesByDate(Observer<List<LaifudaoJoke>> observer, String keyDate){
        Cursor cursor = database.query("laifudao_joke", null, "keyDate=?", new String[]{keyDate}, null, null, null);
        if (cursor.moveToFirst()){
            List<LaifudaoJoke> jokes = new ArrayList<>();
            do {
                LaifudaoJoke joke = new LaifudaoJoke();
                joke.setKeyDate(keyDate);
                joke.setContent(cursor.getString(cursor.getColumnIndex("content")));
                joke.setPoster(cursor.getString(cursor.getColumnIndex("poster")));
                joke.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                joke.setUrl(cursor.getString(cursor.getColumnIndex("url")));

                jokes.add(joke);
            }while (cursor.moveToNext());

            observer.onNext(jokes);
            observer.onComplete();
        }
        cursor.close();
    }

    /**
     * 查询某天所有的funny pic
     * @param observer
     * @param keyDate
     */
    public void selectAllFunnyPicsByDate(Observer<List<LaifudaoPic>> observer, String keyDate){
        Cursor cursor = database.query("laifudao_pic", null, "keyDate=?", new String[]{keyDate}, null, null, null);
        if (cursor.moveToFirst()){
            List<LaifudaoPic> pics = new ArrayList<>();
            do {
                LaifudaoPic pic = new LaifudaoPic();
                pic.setKeyDate(keyDate);
                pic.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                pic.setClassX(cursor.getString(cursor.getColumnIndex("class")));
                pic.setThumburl(cursor.getString(cursor.getColumnIndex("thumburl")));
                pic.setSourceurl(cursor.getString(cursor.getColumnIndex("sourceurl")));
                pic.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                pic.setHeight(cursor.getInt(cursor.getColumnIndex("height")));
                pic.setWidth(cursor.getInt(cursor.getColumnIndex("width")));

                pics.add(pic);
            }while (cursor.moveToNext());

            observer.onNext(pics);
            observer.onComplete();
        }
        cursor.close();
    }

    /**
     * 查询某天所有的头条
     * @param observer
     * @param keyDate
     */
    public void selectAllJuheNewsByDate(Observer<List<JuheNews.ResultBean.DataBean>> observer, String keyDate){
        Cursor cursor = database.query("juhe_news", null, "keyDate=?", new String[]{keyDate}, null, null, null);
        if (cursor.moveToFirst()){
            List<JuheNews.ResultBean.DataBean> newsList = new ArrayList<>();
            do {
                JuheNews.ResultBean.DataBean news = new JuheNews.ResultBean.DataBean();
                news.setKeyDate(keyDate);
                news.setDate(cursor.getString(cursor.getColumnIndex("date")));
                news.setUniquekey(cursor.getString(cursor.getColumnIndex("uniquekey")));
                news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                news.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                news.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                news.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s")));
                news.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02")));
                news.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03")));
                news.setUrl(cursor.getString(cursor.getColumnIndex("url")));

                newsList.add(news);
            }while (cursor.moveToNext());

            observer.onNext(newsList);
            observer.onComplete();
        }
        cursor.close();
    }
}
