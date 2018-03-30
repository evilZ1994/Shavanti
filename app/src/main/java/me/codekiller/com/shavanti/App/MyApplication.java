package me.codekiller.com.shavanti.App;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import me.codekiller.com.shavanti.Utils.SDCardUtil;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        SDCardUtil.initDirectories();
    }
}
