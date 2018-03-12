package me.codekiller.com.shavanti.UI.FunnyPicMore;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.BaseBean;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

/**
 * Created by Lollipop on 2018/3/12.
 */

public interface MoreFunnyPicContract {

    interface View extends BaseView<Presenter>{
        void showResults();
    }

    interface Presenter extends BasePresenter<View>{
        void loadPics(List<BaseBean> beans, String date);
    }
}
