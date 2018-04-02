package me.codekiller.com.shavanti.UI.JuheNewsDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import me.codekiller.com.shavanti.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JuheNewsDetailFragment extends Fragment implements JuheNewsDetailContract.View{
    private WebView webView;

    private String toolbar_title;
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;
    private JuheNews.ResultBean.DataBean bean;
    private JuheNewsDetailContract.Presenter presenter;

    private boolean isBookmarked = false;
    private Menu menu;

    public JuheNewsDetailFragment() {
        // Required empty public constructor
    }

    public static JuheNewsDetailFragment newInstance(){
        return new JuheNewsDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_juhe_news_detail, container, false);

        //必须设置这个选项，否则onCreateOptionsMenu()方法不执行
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        toolbar_title = bundle.getString("toolbar_title");
        uniquekey = bundle.getString("uniquekey");
        title = bundle.getString("title");
        date = bundle.getString("date");
        category = bundle.getString("category");
        author_name = bundle.getString("author_name");
        url = bundle.getString("url");
        thumbnail_pic_s = bundle.getString("thumbnail_pic_s");
        thumbnail_pic_s02 = bundle.getString("thumbnail_pic_s02");
        thumbnail_pic_s03 = bundle.getString("thumbnail_pic_s03");
        bean = new JuheNews.ResultBean.DataBean();
        bean.setTitle(title);
        bean.setDate(date);
        bean.setCategory(category);
        bean.setAuthor_name(author_name);
        bean.setUrl(url);
        bean.setThumbnail_pic_s(thumbnail_pic_s);
        bean.setThumbnail_pic_s02(thumbnail_pic_s02);
        bean.setThumbnail_pic_s03(thumbnail_pic_s03);
        bean.setUniquekey(uniquekey);

        webView = view.findViewById(R.id.news_detail_web_view);
        webView.loadUrl(url);

        presenter.checkIsBookmarked(url);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_detail, menu);
        if (isBookmarked){
            menu.findItem(R.id.news_detail_menu_bookmarks).setIcon(R.drawable.ic_bookmark_white_24dp);
        }
        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.news_detail_menu_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, toolbar_title +url);
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_to)));
        }else if (item.getItemId() == R.id.news_detail_menu_bookmarks){
            if (!isBookmarked) {
                presenter.bookmark(bean);
            }else {
                presenter.cancelBookmark(url);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(JuheNewsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCheckIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    @Override
    public void onBookmarked() {
        isBookmarked = true;
        if (menu != null) {
            menu.findItem(R.id.news_detail_menu_bookmarks).setIcon(R.drawable.ic_bookmark_white_24dp);
        }
    }

    @Override
    public void onCancelBookmarked() {
        isBookmarked = false;
        if (menu != null) {
            menu.findItem(R.id.news_detail_menu_bookmarks).setIcon(R.drawable.ic_bookmark_border_white_24dp);
        }
    }
}
