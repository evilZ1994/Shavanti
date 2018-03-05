package me.codekiller.com.shavanti.View.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import me.codekiller.com.shavanti.Utils.FontCache;

/**
 * Created by Lollipop on 2018/2/27.
 */

public class DayCountTextView extends AppCompatTextView {
    public DayCountTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public DayCountTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public DayCountTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context){
        Typeface typeface = FontCache.getTypeface("Signika-Bold.otf", context);
        setTypeface(typeface);
    }
}
