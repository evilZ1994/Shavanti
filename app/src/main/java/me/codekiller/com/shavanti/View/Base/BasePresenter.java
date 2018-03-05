package me.codekiller.com.shavanti.View.Base;

/**
 * Created by Lollipop on 2018/2/28.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();

    boolean isViewAttached();
}

