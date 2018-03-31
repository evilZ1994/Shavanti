package me.codekiller.com.shavanti.UI.FunnyPicMore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.Utils.ActivityUtils;
import me.codekiller.com.shavanti.Utils.DateUtil;

public class MoreFunnyPicActivity extends AppCompatActivity {
    private String date;
    private MoreFunnyPicContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_funny_pic);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        initViews();

        MoreFunnyPicFragment funnyPicFragment = (MoreFunnyPicFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (funnyPicFragment == null){
            funnyPicFragment = MoreFunnyPicFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            funnyPicFragment.setArguments(bundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), funnyPicFragment, R.id.contentFrame);
        }
        presenter = new MoreFunnyPicPresenter(this, funnyPicFragment);
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.funny_pic_title) + "·" + DateUtil.dateFormat(date, this));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
