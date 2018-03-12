package me.codekiller.com.shavanti.UI.FunnyPicMore;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import me.codekiller.com.shavanti.Model.Data.SQLiteManager;

/**
 * Created by Lollipop on 2018/3/12.
 */

public class MoreFunnyPicPresenter implements MoreFunnyPicContract.Presenter {

    private Context context;
    private MoreFunnyPicContract.View view;
    private SQLiteManager sqLiteManager;

    public MoreFunnyPicPresenter(Context context, MoreFunnyPicContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        sqLiteManager = SQLiteManager.getInstance(context);
    }

    @Override
    public void loadPics(final List<BaseBean> beans, String date) {
        Observer<List<LaifudaoPic>> observer = new Observer<List<LaifudaoPic>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<LaifudaoPic> laifudaoPics) {
                if (laifudaoPics != null && !laifudaoPics.isEmpty()){
                    beans.addAll(laifudaoPics);
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
        sqLiteManager.selectAllFunnyPicsByDate(observer, date);
    }
}
