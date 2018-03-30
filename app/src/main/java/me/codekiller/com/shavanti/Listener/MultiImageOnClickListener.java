package me.codekiller.com.shavanti.Listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import me.codekiller.com.shavanti.UI.ImageView.MultiImageActivity;

public class MultiImageOnClickListener implements View.OnClickListener {
    private Context context;
    private int index;
    private ArrayList<String> images;

    public MultiImageOnClickListener(Context context, int index, ArrayList<String> images){
        this.context = context;
        this.index = index;
        this.images = images;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, MultiImageActivity.class);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }
}
