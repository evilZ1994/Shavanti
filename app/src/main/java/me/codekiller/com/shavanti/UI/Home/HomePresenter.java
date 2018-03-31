package me.codekiller.com.shavanti.UI.Home;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.DateTitle;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.Model.Bean.OneArticle;
import me.codekiller.com.shavanti.Model.Bean.OnePic;
import me.codekiller.com.shavanti.Model.Data.DataManager;
import me.codekiller.com.shavanti.Model.Data.SQLiteManager;
import me.codekiller.com.shavanti.Utils.DateUtil;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View view;
    private Context context;
    private DataManager dataManager;
    private SQLiteManager sqLiteManager;
    private List<BaseBean> beans;

    public HomePresenter(Context context, HomeContract.View view){
        this.context = context;
        this.dataManager = DataManager.getInstance(context);
        this.sqLiteManager = SQLiteManager.getInstance(context);
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadLocal(List<BaseBean> beanList) {
        this.beans = beanList;
        Consumer<List<BaseBean>> consumer = new Consumer<List<BaseBean>>() {
            @Override
            public void accept(List<BaseBean> localBeans) throws Exception {
                beans.addAll(localBeans);
                view.showResults();
                view.loadToday();
            }
        };
        sqLiteManager.loadLocal(consumer);
    }

    @Override
    public void loadToday(List<BaseBean> beans) {
        this.beans = beans;

        DateTitle dateTitle = new DateTitle();
        Date today = new Date();
        dateTitle.setKeyDate(DateUtil.dateFormat(today, context));

        if (today.getTime()>=DateUtil.getLimitTime()){
            //把最新消息添加到最前
            if (!beans.contains(dateTitle)) {
                beans.add(0, dateTitle);
                storeKeyDate();
            }
            //以下添加进list时，index都为1
            //判断是否已经加载
            Date yesterday = new Date(today.getTime()-24*60*60*1000);
            DateTitle yesterTitle = new DateTitle();
            yesterTitle.setKeyDate(DateUtil.dateFormat(yesterday, context));
            int jokeFlag = 0;
            int funnyPicFlag = 0;
            int juheFlag = 0;
            int onePicFlag = 0;
            int oneArtFlag = 0;
            for (BaseBean bean : beans){
                if (bean.equals(yesterTitle)){
                    //提前终止
                    break;
                }else if (bean instanceof LaifudaoJoke){
                    jokeFlag = 1;
                }else if (bean instanceof LaifudaoPic){
                    funnyPicFlag = 1;
                }else if (bean instanceof JuheNews.ResultBean.DataBean){
                    juheFlag = 1;
                }else if (bean instanceof OnePic){
                    onePicFlag = 1;
                }else if (bean instanceof OneArticle){
                    oneArtFlag = 1;
                }
            }
            if (jokeFlag == 0){
                loadJoke();
            }
            if (funnyPicFlag == 0){
                loadFunnyPic();
            }
            if (juheFlag == 0) {
                loadJuheNews();
            }
            if (onePicFlag == 0){
                loadOnePic();
            }
            if (oneArtFlag == 0){
                loadOneArticle();
            }
        }
    }

    private void storeKeyDate() {
        sqLiteManager.storeKeyDate();
    }

    @Override
    public void loadJoke() {
        Observer<List<LaifudaoJoke>> observer = new Observer<List<LaifudaoJoke>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<LaifudaoJoke> laifudaoJokes) {
                if (!laifudaoJokes.isEmpty()){
                    for (LaifudaoJoke joke : laifudaoJokes){
                        joke.setKeyDate(DateUtil.dateFormat(new Date(), context));
                    }
                    if (!beans.contains(laifudaoJokes.get(0))) {
                        beans.add(1, laifudaoJokes.get(0));
                        //保存到本地
                        sqLiteManager.addLaifudaoJokes(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                //暂时不作处理
                            }
                        }, laifudaoJokes);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                view.showResults();
            }
        };
        dataManager.getLaifudaoJokes(observer);
    }

    @Override
    public void loadFunnyPic() {
        Observer<List<LaifudaoPic>> observer = new Observer<List<LaifudaoPic>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<LaifudaoPic> laifudaoPics) {
                if (!laifudaoPics.isEmpty()){
                    for (LaifudaoPic pic : laifudaoPics){
                        pic.setKeyDate(DateUtil.dateFormat(new Date(), context));
                    }
                    if (!beans.contains(laifudaoPics.get(0))) {
                        beans.add(1, laifudaoPics.get(0));
                        //保存到本地
                        sqLiteManager.addLaifudaoPic(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                            }
                        }, laifudaoPics);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                view.showResults();
            }
        };
        dataManager.getLaifudaoPics(observer);
    }

    @Override
    public void loadJuheNews() {
        Observer<JuheNews> observer = new Observer<JuheNews>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(JuheNews juheNews) {
                List<JuheNews.ResultBean.DataBean> topNews = juheNews.getResult().getData();
                if (juheNews.getResult() != null && topNews != null && !topNews.isEmpty()) {
                    for (JuheNews.ResultBean.DataBean news : topNews){
                        news.setKeyDate(DateUtil.dateFormat(new Date(), context));
                    }
                    if(!beans.contains(topNews.get(0))) {
                        beans.add(1, topNews.get(0));
                        //保存到本地
                        sqLiteManager.addJuheNews(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                            }
                        }, topNews);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                view.showResults();
            }
        };
        dataManager.getJuheNews(observer);
    }

    @Override
    public void loadOnePic() {
        Observer<OnePic> observer = new Observer<OnePic>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(OnePic onePic) {
                onePic.setKeyDate(DateUtil.dateFormat(new Date(), context));
                if (!beans.contains(onePic)) {
                    beans.add(1, onePic);
                    sqLiteManager.addOnePic(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                        }
                    }, onePic);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("OnePic", e.getMessage());
            }

            @Override
            public void onComplete() {
                view.showResults();
            }
        };
        dataManager.getOnePic(observer);
    }

    @Override
    public void loadOneArticle() {
        Observer<OneArticle> observer = new Observer<OneArticle>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(OneArticle oneArticle) {
                oneArticle.setKeyDate(DateUtil.dateFormat(new Date(), context));
                if (!beans.contains(oneArticle)){
                    beans.add(1, oneArticle);
                    sqLiteManager.addOneArticle(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                        }
                    }, oneArticle);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("OneArticle", e.getMessage());
            }

            @Override
            public void onComplete() {
                view.showResults();
            }
        };
        dataManager.getOneArticle(observer);
    }
}
