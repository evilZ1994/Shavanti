package me.codekiller.com.shavanti.View.Base;

import android.view.View;

/**
 * Created by Lollipop on 2018/2/28.
 */

public interface BaseView<T> {
    void initViews(View view);

    void setPresenter(T presenter);
}
