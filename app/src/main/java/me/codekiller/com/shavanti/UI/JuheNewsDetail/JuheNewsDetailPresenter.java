package me.codekiller.com.shavanti.UI.JuheNewsDetail;

import android.content.Context;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.Model.Data.SQLiteManager;

public class JuheNewsDetailPresenter implements JuheNewsDetailContract.Presenter {
    private JuheNewsDetailContract.View view;
    private Context context;
    private SQLiteManager sqLiteManager;

    public JuheNewsDetailPresenter(JuheNewsDetailContract.View view, Context context){
        this.view = view;
        view.setPresenter(this);
        this.context = context;
        sqLiteManager = SQLiteManager.getInstance(context);
    }

    @Override
    public void checkIsBookmarked(String url) {
        sqLiteManager.selectJuheNewsBookmarkByUrl(new Consumer<JuheNews.ResultBean.DataBean>() {
            @Override
            public void accept(JuheNews.ResultBean.DataBean bean) throws Exception {
                view.onCheckIsBookmarked(bean.getUrl() != null);
            }
        }, url);
    }

    @Override
    public void bookmark(JuheNews.ResultBean.DataBean bean) {
        sqLiteManager.addJuheNewsBookmark(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.onBookmarked();
            }
        }, bean);
    }

    @Override
    public void cancelBookmark(String url) {
        sqLiteManager.deleteJuheNewsBookmarkByUrl(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.onCancelBookmarked();
            }
        }, url);
    }
}
