package me.codekiller.com.shavanti.UI.JokeMore;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

/**
 * Created by Lollipop on 2018/3/8.
 */

public interface MokeJokeContract {
    interface View extends BaseView<Presenter>{
        void showResults();
    }

    interface Presenter extends BasePresenter<View>{
        void loadJokes(List<BaseBean> beans, String date);
    }
}
