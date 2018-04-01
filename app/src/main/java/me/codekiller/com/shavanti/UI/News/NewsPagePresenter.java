package me.codekiller.com.shavanti.UI.News;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Data.DataManager;

public class NewsPagePresenter implements NewsPageContract.Presenter {
    private NewsPageContract.View view;
    private DataManager dataManager;

    public NewsPagePresenter(NewsPageContract.View view, Context context){
        this.view = view;
        view.setPresenter(this);
        dataManager = DataManager.getInstance(context);
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

            }

            @Override
            public void onComplete() {
                view.stopLoading();
            }
        }, type);
    }
}
