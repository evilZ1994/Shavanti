package me.codekiller.com.shavanti.UI.SimpleNews;

import java.util.List;

import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

public interface NewsPageContract {
    interface View extends BaseView<Presenter>{

        void showLoading();

        void stopLoading();

        void onLocalLoaded(List<JuheNews.ResultBean.DataBean> dataBeans);

        void onDataLoaded(List<JuheNews.ResultBean.DataBean> dataBeans);

        void onCacheCleared(int count);
    }

    interface Presenter extends BasePresenter<View>{
        void loadLocal(String type);

        void loadData(String type);

        void saveNewData(List<JuheNews.ResultBean.DataBean> dataBeans);

        void clearNewsCache();
    }
}
