package me.codekiller.com.shavanti.View.UI.Home;

import android.content.Context;

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
        this.dataManager = DataManager.getInstance();
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
        dateTitle.setKeyDate(DateUtil.dateFormat(new Date(), context));

        if (!beans.contains(dateTitle)) {
            beans.add(dateTitle);
            storeKeyDate();
            loadJoke();
            loadFunnyPic();
            loadJuheNews();
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
                    beans.add(laifudaoJokes.get(0));
                    //保存到本地
                    sqLiteManager.addLaifudaoJokes(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            //暂时不作处理
                        }
                    }, laifudaoJokes);
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
                    beans.add(laifudaoPics.get(0));
                    //保存到本地
                    sqLiteManager.addLaifudaoPic(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                        }
                    }, laifudaoPics);
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
                    beans.add(topNews.get(0));
                    //保存到本地
                    sqLiteManager.addJuheNews(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                        }
                    }, topNews);
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
}
