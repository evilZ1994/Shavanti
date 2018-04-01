package me.codekiller.com.shavanti.UI.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import main.java.com.UpYun;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.UI.Home.HomeFragment;
import me.codekiller.com.shavanti.UI.Home.HomePresenter;
import me.codekiller.com.shavanti.UI.News.NewsMainActivity;
import me.codekiller.com.shavanti.Utils.ActivityUtils;
import me.codekiller.com.shavanti.Utils.CommonUtil;
import me.codekiller.com.shavanti.Utils.DateUtil;
import me.codekiller.com.shavanti.Utils.FileUtil;
import me.codekiller.com.shavanti.Utils.SDCardUtil;
import me.codekiller.com.shavanti.View.CustomViews.DayCountTextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityContract.View {
    private NavigationView navigationView;
    private DayCountTextView dayCountTextView;
    private ImageView homeBgImg;
    private ImageView navHeaderBgImg;
    private DrawerLayout drawer;

    private HomeFragment homeFragment;
    private HomePresenter homePresenter;

    private int checked_item = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.contentFrame);
        }
        homePresenter = new HomePresenter(this, homeFragment);

        MainActivityPresenter presenter = new MainActivityPresenter(this, this);
        //检查首页背景图
        presenter.checkHomePic();
        //修改背景图
        changeBackgroundPic();

        writeDay();
    }

    /**
     * 随机改变背景图片，主页背景和抽屉头部背景都是随机的
     */
    private void changeBackgroundPic() {
        List<String> picNames = FileUtil.getFileNames(SDCardUtil.getHomePicPath());
        if (!picNames.isEmpty()) {
            Random random = new Random();
            int index1 = random.nextInt(picNames.size());
            int index2 = random.nextInt(picNames.size());
            String picPath1 = SDCardUtil.getHomePicPath() + picNames.get(index1);
            String picPath2 = SDCardUtil.getHomePicPath() + picNames.get(index2);
            homeBgImg.setImageURI(Uri.fromFile(new File(picPath1)));
            navHeaderBgImg.setImageURI(Uri.fromFile(new File(picPath2)));
        }
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //取消item的选中状态
                if (checked_item != -1){
                    navigationView.getMenu().findItem(checked_item).setChecked(false);
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dayCountTextView = findViewById(R.id.day_count);
        dayCountTextView.setText(String.valueOf(DateUtil.CountDays()));

        homeBgImg = findViewById(R.id.home_bg_img);
        navigationView = findViewById(R.id.nav_view);
        navHeaderBgImg = navigationView.getHeaderView(0).findViewById(R.id.nav_header_bg_img);

    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_simple_news) {
            //极简新闻
            Intent intent = new Intent(this, NewsMainActivity.class);
            startActivity(intent);
            //记住当前点击的item，按返回键时取消选中状态
            checked_item = id;
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    @Override
    protected void onResume() {
        changeBackgroundPic();
        super.onResume();
    }

    private void writeDay() {
        SharedPreferences preferences = getSharedPreferences("day", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("k", "54a9d4addb895fd4");
        editor.apply();
    }
}
