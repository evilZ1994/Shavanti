package me.codekiller.com.shavanti.UI.JokeMore;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import me.codekiller.com.shavanti.Model.Data.SQLiteManager;

/**
 * Created by Lollipop on 2018/3/11.
 */

public class MoreJokePresenter implements MokeJokeContract.Presenter {
    private Context context;
    private MokeJokeContract.View view;
    private SQLiteManager sqLiteManager;

    public MoreJokePresenter(Context context, MokeJokeContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        sqLiteManager = SQLiteManager.getInstance(context);
    }

    @Override
    public void loadJokes(final List<BaseBean> beans, String date) {
        Observer<List<LaifudaoJoke>> observer = new Observer<List<LaifudaoJoke>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<LaifudaoJoke> jokes) {
                if (jokes != null && !jokes.isEmpty()){
                    beans.addAll(jokes);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                view.showResults();
            }
        };
        sqLiteManager.selectAllJokesByDate(observer, date);
    }
}
