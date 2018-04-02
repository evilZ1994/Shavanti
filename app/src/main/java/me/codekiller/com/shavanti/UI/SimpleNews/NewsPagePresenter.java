package me.codekiller.com.shavanti.UI.SimpleNews;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Data.DataManager;
import me.codekiller.com.shavanti.Model.Data.SQLiteHelper;
import me.codekiller.com.shavanti.Model.Data.SQLiteManager;

public class NewsPagePresenter implements NewsPageContract.Presenter {
    private NewsPageContract.View view;
    private DataManager dataManager;
    private SQLiteManager sqLiteManager;
    private Context context;

    public NewsPagePresenter(NewsPageContract.View view, Context context){
        this.view = view;
        view.setPresenter(this);
        this.context = context;
        dataManager = DataManager.getInstance(context);
        sqLiteManager = SQLiteManager.getInstance(context);
    }

    @Override
    public void loadLocal(String typeCN) {
        sqLiteManager.selectJuheNewsCacheByType(new Consumer<List<JuheNews.ResultBean.DataBean>>() {
            @Override
            public void accept(List<JuheNews.ResultBean.DataBean> dataBeans) throws Exception {
                view.onLocalLoaded(dataBeans);
            }
        }, typeCN);
    }

    @Override
    public void loadData(String type) {
        view.showLoading();

        dataManager.getJuheNewsByType(new Observer<JuheNews>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(JuheNews juheNews) {
                if (juheNews.getError_code() == 0){
                    view.onDataLoaded(juheNews.getResult().getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopLoading();
            }

            @Override
            public void onComplete() {
                view.stopLoading();
            }
        }, type);
    }

    @Override
    public void saveNewData(List<JuheNews.ResultBean.DataBean> dataBeans) {
        sqLiteManager.addJuheNewsCache(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, dataBeans);
    }

    @Override
    public void clearNewsCache() {
        sqLiteManager.clearJuheNewsCache(new Consumer<Integer>() {
            @Override
            public void accept(Integer count) throws Exception {
                view.onCacheCleared(count);
            }
        });
    }
}
