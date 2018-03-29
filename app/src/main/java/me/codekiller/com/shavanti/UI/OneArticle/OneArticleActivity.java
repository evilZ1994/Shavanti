package me.codekiller.com.shavanti.UI.OneArticle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.TextView;

import me.codekiller.com.shavanti.R;

public class OneArticleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView descriptionView;
    private WebView webView;

    private String title;
    private String description;
    private String author;
    private String article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_article);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        author = intent.getStringExtra("author");
        article = intent.getStringExtra("article");

        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setSubtitle(getResources().getString(R.string.author_pre)+author);
        setSupportActionBar(toolbar);

        descriptionView = findViewById(R.id.description);
        descriptionView.setText(description);

        webView = findViewById(R.id.web_view);
        webView.loadData(article, "text/html;charset=utf-8","utf-8");
    }
}
