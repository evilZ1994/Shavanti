package me.codekiller.com.shavanti.Model.Data;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import main.java.com.UpYun;
import me.codekiller.com.shavanti.Model.Api.Api;
import me.codekiller.com.shavanti.Model.Api.RetrofitHelper;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.Model.Bean.OneArticle;
import me.codekiller.com.shavanti.Model.Bean.OnePic;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.Utils.CommonUtil;
import me.codekiller.com.shavanti.Utils.DateUtil;
import me.codekiller.com.shavanti.Utils.FileUtil;
import me.codekiller.com.shavanti.Utils.SDCardUtil;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class DataManager {
    private Context context;

    private static DataManager instance;
    public static String juheAppKey = "e4e32051e4758d3cc87a769d796d1f24";

    public DataManager(Context context){
        this.context = context;
    }

    public static DataManager getInstance(Context context){
        if (instance == null){
            instance = new DataManager(context);
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

    /**
     * 获取One一个的图片
     * @param observer
     */
    public void getOnePic(Observer<OnePic> observer){
        Observable.create(new ObservableOnSubscribe<OnePic>() {
            @Override
            public void subscribe(ObservableEmitter<OnePic> emitter) throws Exception {
                String url = Api.ONE_PIC_API + DateUtil.getOnePicIndex();
                Document document = Jsoup.connect(url).get();
                String title = document.selectFirst("title").text();
                if (!title.contains(context.getResources().getString(R.string.not_found_note))){
                    OnePic onePic = new OnePic();
                    onePic.setUrl(url);
                    onePic.setDescription(document.selectFirst("meta[name=description]").attr("content"));
                    onePic.setImgUrl(document.selectFirst("meta[property=og:image]").attr("content"));
                    emitter.onNext(onePic);
                }else {
                    emitter.onError(new Throwable(context.getResources().getString(R.string.not_found_note)));
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取One一个的文章
     * @param observer
     */
    public void getOneArticle(Observer<OneArticle> observer){
        Observable.create(new ObservableOnSubscribe<OneArticle>() {
            @Override
            public void subscribe(ObservableEmitter<OneArticle> emitter) throws Exception {
                String url = Api.ONE_ARTICAL_API + DateUtil.getOneArticalIndex();
                Document document = Jsoup.connect(url).get();
                String title = document.selectFirst("title").text();
                if (!title.contains(context.getResources().getString(R.string.not_found_note))){
                    OneArticle article = new OneArticle();
                    article.setUrl(url);
                    String articleTitle = document.selectFirst("meta[property=og:title]").attr("content");
                    article.setTitle(articleTitle.replace(context.getResources().getString(R.string.one), ""));
                    article.setDescription(document.selectFirst("meta[name=description]").attr("content"));
                    String author = document.selectFirst("p.articulo-autor").text();
                    article.setAuthor(author.replace(context.getResources().getString(R.string.author_pre), ""));
                    article.setArticle(document.selectFirst("div.articulo-contenido").html());

                    emitter.onNext(article);
                }else {
                    emitter.onError(new Throwable(context.getResources().getString(R.string.not_found_note)));
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 检查又拍云的图片列表和本地主页背景图片列表，统计未下载的图片名
     */
    public void checkHomePic(Consumer<List<String>> consumer){
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                UpYun upYun = new UpYun(CommonUtil.getB(context), CommonUtil.getN(context), CommonUtil.getP(context));
                List<UpYun.FolderItem> folderItems = upYun.readDir(Api.HOME_PIC_PATH);
                List<String> localPicNames = FileUtil.getFileNames(SDCardUtil.getHomePicPath());
                List<String> lackPics = new ArrayList<>();
                for(UpYun.FolderItem item : folderItems){
                    if (!localPicNames.contains(item.name)){
                        lackPics.add(item.name);
                    }
                }
                emitter.onNext(lackPics);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 下载又拍云的图片
     * @param lackPics 图片列表
     */
    public void downloadHomePic(Consumer<String> consumer, final List<String> lackPics){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                for (String picName : lackPics){
                    String remotePath = Api.HOME_PIC_PATH + picName;
                    String localPath = SDCardUtil.getHomePicPath() + picName;
                    UpYun upYun = new UpYun(CommonUtil.getB(context), CommonUtil.getN(context), CommonUtil.getP(context));
                    File file = new File(localPath);
                    upYun.readFile(remotePath, file);
                }
                emitter.onNext("done");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
