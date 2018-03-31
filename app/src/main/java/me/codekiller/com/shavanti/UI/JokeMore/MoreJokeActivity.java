package me.codekiller.com.shavanti.UI.JokeMore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.Utils.ActivityUtils;
import me.codekiller.com.shavanti.Utils.DateUtil;

public class MoreJokeActivity extends AppCompatActivity {
    private String date;
    private MoreJokePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_joke);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        initViews();

        MoreJokeFragment fragment = (MoreJokeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null){
            fragment = MoreJokeFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            fragment.setArguments(bundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
        presenter = new MoreJokePresenter(this, fragment);
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.joke_title)+"·"+ DateUtil.dateFormat(date, this));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
