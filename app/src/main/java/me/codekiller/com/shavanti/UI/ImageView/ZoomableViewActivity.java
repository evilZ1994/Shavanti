package me.codekiller.com.shavanti.UI.ImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.Utils.CommonUtil;
import me.codekiller.com.shavanti.Utils.FileUtil;
import me.codekiller.com.shavanti.Utils.FrescoUtil;
import me.codekiller.com.shavanti.Utils.SDCardUtil;
import me.codekiller.com.shavanti.View.Zoomable.ZoomableDraweeView;

public class ZoomableViewActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private BottomSheetDialog dialog;
    private ZoomableDraweeView zoomableDraweeView;
    private Toolbar toolbar;

    private Uri imageUri;
    private boolean isHide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomable_view);

        Intent intent = getIntent();
        imageUri = Uri.parse(intent.getStringExtra("image_uri"));
        String title = intent.getStringExtra("title");

        toolbar = findViewById(R.id.zoomable_view_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        //toolbar必须在setSupportActionBar方法后设置这个监听器，否则无效
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        appBarLayout = findViewById(R.id.zoomable_view_app_bar);

        zoomableDraweeView = findViewById(R.id.zoomable_view);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUri)
                .build();
        zoomableDraweeView.setController(controller);
        zoomableDraweeView.setIsLongpressEnabled(true);
        zoomableDraweeView.setTapListener(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //显示隐藏标题栏和状态栏
                if (isHide){
                    cancelFullScreen();
                    appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                    isHide = false;
                }else {
                    setFullScreen();
                    appBarLayout.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                    isHide = true;
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //显示菜单
                showBottomSheetDialog();
                super.onLongPress(e);
            }
        });
    }

    /**
     * 设置全屏
     */
    private void setFullScreen(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     */
    private void cancelFullScreen(){
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showBottomSheetDialog(){
        if (dialog == null) {
            dialog = new BottomSheetDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.image_view_bottom_sheet, null);
            LinearLayout saveItem = view.findViewById(R.id.save_pic_item);
            LinearLayout shareItem = view.findViewById(R.id.share_pic_item);

            saveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //保存图片到本地
                    String path = SDCardUtil.getOnePicPath()+ CommonUtil.getOnePicName(imageUri.toString())+".jpeg";
                    FrescoUtil.savePic(path, imageUri, ZoomableViewActivity.this);
                    Toast.makeText(ZoomableViewActivity.this, getResources().getString(R.string.file_save_to)+path, Toast.LENGTH_SHORT).show();

                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            shareItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    //先将图片缓存到本地文件中
                    String path = SDCardUtil.getCachePath()+ CommonUtil.getOnePicName(imageUri.toString())+".jpeg";
                    FrescoUtil.savePic(path, imageUri, ZoomableViewActivity.this);
                    File file = new File(path);
                    if (file.exists()) {
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        shareIntent.setType("image/*");
                        startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_to)));
                    }else {
                        Toast.makeText(ZoomableViewActivity.this, R.string.file_format_wrong, Toast.LENGTH_SHORT).show();
                    }

                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });

            dialog.setContentView(view);
        }
        dialog.show();
    }

    /**
     * toolbar作为ActionBar使用时，需要复写这两个方法实现菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.more_vertical) {
            showBottomSheetDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}
