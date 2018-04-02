package me.codekiller.com.shavanti.UI.JuheNewsDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.Utils.ActivityUtils;

public class JuheNewsDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juhe_news_detail);

        Intent intent = getIntent();
        toolbar_title = intent.getStringExtra("toolbar_title");
        uniquekey = intent.getStringExtra("uniquekey");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        category = intent.getStringExtra("category");
        author_name = intent.getStringExtra("author_name");
        url = intent.getStringExtra("url");
        thumbnail_pic_s = intent.getStringExtra("thumbnail_pic_s");
        thumbnail_pic_s02 = intent.getStringExtra("thumbnail_pic_s02");
        thumbnail_pic_s03 = intent.getStringExtra("thumbnail_pic_s03");

        toolbar = findViewById(R.id.news_detail_toolbar);
        toolbar.setTitle(toolbar_title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        JuheNewsDetailFragment fragment = (JuheNewsDetailFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null){
            fragment = JuheNewsDetailFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("toolbar_title", toolbar_title);
            bundle.putString("uniquekey", uniquekey);
            bundle.putString("title", title);
            bundle.putString("date", date);
            bundle.putString("category", category);
            bundle.putString("author_name", author_name);
            bundle.putString("url", url);
            bundle.putString("thumbnail_pic_s", thumbnail_pic_s);
            bundle.putString("thumbnail_pic_s02", thumbnail_pic_s02);
            bundle.putString("thumbnail_pic_s03", thumbnail_pic_s03);
            fragment.setArguments(bundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.container);
        }
        new JuheNewsDetailPresenter(fragment, this);
    }
}
