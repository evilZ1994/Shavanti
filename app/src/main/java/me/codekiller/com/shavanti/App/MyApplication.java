package me.codekiller.com.shavanti.App;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import me.codekiller.com.shavanti.Utils.SDCardUtil;
import okhttp3.OkHttpClient;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        SDCardUtil.initDirectories();
        //初始化Stetho工具
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}
