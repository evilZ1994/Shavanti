package me.codekiller.com.shavanti.UI.ImageView;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import me.codekiller.com.shavanti.Adapter.MultiImagePagerAdapter;
import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.Utils.CommonUtil;
import me.codekiller.com.shavanti.Utils.FrescoUtil;
import me.codekiller.com.shavanti.Utils.SDCardUtil;

public class MultiImageActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private BottomSheetDialog dialog;

    private List<String> images;
    private MultiImagePagerAdapter adapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_image);

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        index = intent.getIntExtra("index", 1);

        appBarLayout = findViewById(R.id.multi_img_app_bar);
        toolbar = findViewById(R.id.multi_img_toolbar);
        viewPager = findViewById(R.id.multi_img_view_pager);

        toolbar.setTitle(index + "/" + images.size());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new MultiImagePagerAdapter(this, images);
        adapter.setOnLongPressListener(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                showBottomSheetDialog();
                super.onLongPress(e);
            }
        });
        viewPager.setAdapter(adapter);
        //显示点击的图片
        viewPager.setCurrentItem(index-1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position + 1;
                toolbar.setTitle(index + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.more_vertical){
            showBottomSheetDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBottomSheetDialog(){
        if (dialog == null){
            dialog = new BottomSheetDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.image_view_bottom_sheet, null);
            LinearLayout saveItem = view.findViewById(R.id.save_pic_item);
            LinearLayout shareItem = view.findViewById(R.id.share_pic_item);

            saveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //保存图片到本地
                    String path = SDCardUtil.getOnePicPath()+"/"+ CommonUtil.getOnePicName(images.get(index-1))+".jpeg";
                    FrescoUtil.savePic(path, Uri.parse(images.get(index-1)), MultiImageActivity.this);
                    Toast.makeText(MultiImageActivity.this, getResources().getString(R.string.file_save_to)+path, Toast.LENGTH_SHORT).show();
                }
            });
            shareItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    //先将图片缓存到本地文件中
                    String path = SDCardUtil.getCachePath()+"/"+ CommonUtil.getOnePicName(images.get(index-1))+".jpeg";
                    FrescoUtil.savePic(path, Uri.parse(images.get(index-1)), MultiImageActivity.this);
                    File file = new File(path);
                    if (file.exists()) {
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        shareIntent.setType("image/*");
                        startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_to)));
                    }else {
                        Toast.makeText(MultiImageActivity.this, R.string.file_format_wrong, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            dialog.setContentView(view);
        }
        dialog.show();
    }
}
