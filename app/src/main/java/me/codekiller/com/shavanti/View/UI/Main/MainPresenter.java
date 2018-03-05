package me.codekiller.com.shavanti.View.UI.Main;

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

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Context context;
    private DataManager dataManager;
    private SQLiteManager sqLiteManager;
    private List<BaseBean> beans;

    public MainPresenter(Context context){
        this.context = context;
        this.dataManager = DataManager.getInstance();
        this.sqLiteManager = SQLiteManager.getInstance(context);
    }

    @Override
    public void attachView(MainContract.View view) {
        if (!isViewAttached()) {
            this.view = view;
        }
    }

    @Override
    public void detachView() {
        if (isViewAttached()) {
            this.view = null;
        }
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void loadAll(List<BaseBean> beans) {
        view.showLoading();

        this.beans = beans;

        DateTitle dateTitle = new DateTitle();
        dateTitle.setDate(DateUtil.dateFormat(new Date(), context));

        if (!beans.contains(dateTitle)) {
            beans.add(dateTitle);
            loadJoke();
            loadFunnyPic();
            loadJuheNews();
        }
        view.stopLoading();
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
                    beans.add(laifudaoJokes.get(0));
                    //保存到本地
                    sqLiteManager.addLaifudaoJoke(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            //暂时不作处理
                        }
                    }, laifudaoJokes.get(0));
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
                    beans.add(laifudaoPics.get(0));
                    //保存到本地
                    sqLiteManager.addLaifudaoPic(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                        }
                    }, laifudaoPics.get(0));
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
                if (juheNews.getResult() != null && juheNews.getResult().getData() != null && !juheNews.getResult().getData().isEmpty()) {
                    beans.add(juheNews.getResult().getData().get(0));
                    //保存到本地
                    sqLiteManager.addJuheNews(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                        }
                    }, juheNews.getResult().getData().get(0));
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
