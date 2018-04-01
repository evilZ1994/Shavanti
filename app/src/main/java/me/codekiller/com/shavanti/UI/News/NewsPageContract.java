package me.codekiller.com.shavanti.UI.News;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

public interface NewsPageContract {
    interface View extends BaseView<Presenter>{

        void showLoading();

        void stopLoading();

        void onDataLoaded(List<JuheNews.ResultBean.DataBean> dataBeans);
    }

    interface Presenter extends BasePresenter<View>{
        void loadData(String type);
    }
}
