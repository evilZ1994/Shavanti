package me.codekiller.com.shavanti.Model.Data;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.codekiller.com.shavanti.Model.Api.RetrofitHelper;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class DataManager {
    private static DataManager instance;

    public static String juheAppKey = "e4e32051e4758d3cc87a769d796d1f24";

    public static DataManager getInstance(){
        if (instance == null){
            instance = new DataManager();
        }

        return instance;
    }

    /**
     * 获取来福岛笑话
     * @param observer
     */
    public void getLaifudaoJokes(Observer<List<LaifudaoJoke>> observer){
        RetrofitHelper.getlJokeService()
                .requestJokes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取来福岛搞笑图片
     * @param observer
     */
    public void getLaifudaoPics(Observer<List<LaifudaoPic>> observer) {
        RetrofitHelper.getlPicService()
                .requestPics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取聚合数据头条新闻
     * @param observer
     */
    public void getJuheNews(Observer<JuheNews> observer){
        RetrofitHelper.getJuheNewsService()
                .requestJuheNews(juheAppKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
