package me.codekiller.com.shavanti.Model.Api;

import io.reactivex.Observable;
import me.codekiller.com.shavanti.Model.Bean.JuheNews;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Lollipop on 2018/3/1.
 */

public interface JuheNewsService {
    @POST("index")
    @FormUrlEncoded
    Observable<JuheNews> requestJuheNews(@Field("key")String appKey);

    @POST("index")
    @FormUrlEncoded
    Observable<JuheNews> requestJuheNewsByType(@Field("key")String appKey, @Field("type")String type);
}
