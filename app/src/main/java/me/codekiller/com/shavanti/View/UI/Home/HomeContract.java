package me.codekiller.com.shavanti.View.UI.Home;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

/**
 * Created by Lollipop on 2018/2/28.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter>{
        void showLoading();

        void stopLoading();

        void showResults();

        void loadToday();

        void showError(String msg);
    }

    interface Presenter extends BasePresenter<View>{
        void loadLocal(List<BaseBean> beans);

        void loadToday(List<BaseBean> beans);

        void loadJoke();

        void loadFunnyPic();

        void loadJuheNews();
    }
}
