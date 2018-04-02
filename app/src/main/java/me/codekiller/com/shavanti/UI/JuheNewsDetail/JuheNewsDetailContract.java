package me.codekiller.com.shavanti.UI.JuheNewsDetail;

import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.View.Base.BasePresenter;
import me.codekiller.com.shavanti.View.Base.BaseView;

public interface JuheNewsDetailContract {
    interface View extends BaseView<Presenter>{
        void onCheckIsBookmarked(boolean isBookmarked);

        void onBookmarked();

        void onCancelBookmarked();
    }

    interface Presenter extends BasePresenter<View>{
        void checkIsBookmarked(String url);

        void bookmark(JuheNews.ResultBean.DataBean bean);

        void cancelBookmark(String url);
    }
}
