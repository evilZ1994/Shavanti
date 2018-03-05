package me.codekiller.com.shavanti.View.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import me.codekiller.com.shavanti.Utils.FontCache;

/**
 * Created by Lollipop on 2018/2/27.
 */

public class HandWriteTextView extends AppCompatTextView {
    public HandWriteTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public HandWriteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public HandWriteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context){
        Typeface typeface = FontCache.getTypeface("honaji.ttf", context);
        setTypeface(typeface);
    }
}
