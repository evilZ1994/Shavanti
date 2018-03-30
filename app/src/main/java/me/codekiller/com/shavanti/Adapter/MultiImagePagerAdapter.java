package me.codekiller.com.shavanti.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.com.shavanti.R;
import me.codekiller.com.shavanti.View.Zoomable.ZoomableDraweeView;

public class MultiImagePagerAdapter extends PagerAdapter {
    private List<String> images;
    private Context context;
    private List<View> views;

    public MultiImagePagerAdapter(Context context, List<String> images){
        this.context = context;
        this.images = images;
        initViews();
    }

    private void initViews() {
        views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (String image : images) {
            View view = inflater.inflate(R.layout.multi_image_item, null, false);
            ZoomableDraweeView zoomableDraweeView = view.findViewById(R.id.multi_img_item);
            zoomableDraweeView.setController(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(image)).build());
            views.add(view);
        }
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    public void setOnLongPressListener(GestureDetector.SimpleOnGestureListener longPressListener){
        for (View v : views) {
            ZoomableDraweeView zoomableDraweeView = v.findViewById(R.id.multi_img_item);
            zoomableDraweeView.setIsLongpressEnabled(true);
            zoomableDraweeView.setTapListener(longPressListener);
        }
    }
}
