package me.codekiller.com.shavanti.View.UI.Main;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.Model.Bean.DayContent;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

/**
 * Created by Lollipop on 2018/2/28.
 */

public interface MainContract {
    interface View extends BaseView<Presenter>{
        void showLoading();

        void stopLoading();

        void showResults();

        void showError(String msg);
    }

    interface Presenter extends BasePresenter<View>{
        void loadAll(List<BaseBean> beans);

        void loadJoke();

        void loadFunnyPic();

        void loadJuheNews();
    }
}
