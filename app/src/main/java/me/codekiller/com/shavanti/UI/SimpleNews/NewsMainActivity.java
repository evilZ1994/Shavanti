package me.codekiller.com.shavanti.UI.SimpleNews;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.codekiller.com.shavanti.Adapter.NewsPagerAdapter;
import me.codekiller.com.shavanti.R;

public class NewsMainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private NewsPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> titles;
    private List<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.news_main_toolbar);
        toolbar.setTitle(getResources().getString(R.string.simple_news));
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.news_main_tab);
        viewPager = findViewById(R.id.news_main_pager);
        titles = Arrays.asList(getResources().getStringArray(R.array.news_tabs));
        types = Arrays.asList(getResources().getStringArray(R.array.news_types));
        fragments = new ArrayList<>();
        for (String title : titles){
            NewsPageFragment fragment = NewsPageFragment.getInstance();
            Bundle bundle = new Bundle();
            bundle.putString("type", types.get(titles.indexOf(title)));
            bundle.putString("typeCN", title);
            fragment.setArguments(bundle);
            new NewsPagePresenter(fragment, this);
            fragments.add(fragment);
        }
        pagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
