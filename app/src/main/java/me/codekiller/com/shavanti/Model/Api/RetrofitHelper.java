package me.codekiller.com.shavanti.Model.Api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class RetrofitHelper {
    private static String laifudaoBaseUrl = "http://api.laifudao.com/open/";
    private static String juheNewsBaseUrl = "http://v.juhe.cn/toutiao/";

    private static OkHttpClient okHttpClient;
    private static LJokeService lJokeService;
    private static LPicService lPicService;
    private static JuheNewsService juheNewsService;

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15*1000, TimeUnit.MILLISECONDS)
                    .readTimeout(15*1000, TimeUnit.MILLISECONDS)
                    .writeTimeout(15*1000, TimeUnit.MILLISECONDS)
                    .build();
        }

        return okHttpClient;
    }

    public static LJokeService getlJokeService(){
        if (lJokeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(laifudaoBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            lJokeService = retrofit.create(LJokeService.class);
        }

        return lJokeService;
    }

    public static LPicService getlPicService(){
        if (lPicService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(laifudaoBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            lPicService = retrofit.create(LPicService.class);
        }

        return lPicService;
    }

    public static JuheNewsService getJuheNewsService(){
        if (juheNewsService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(juheNewsBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            juheNewsService = retrofit.create(JuheNewsService.class);
        }

        return juheNewsService;
    }
}
