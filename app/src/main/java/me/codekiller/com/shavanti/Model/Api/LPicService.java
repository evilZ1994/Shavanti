package me.codekiller.com.shavanti.Model.Api;

import java.util.List;

import io.reactivex.Observable;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoPic;
import retrofit2.http.GET;

/**
 * Created by Lollipop on 2018/2/28.
 */

public interface LPicService {
    @GET("tupian.json")
    Observable<List<LaifudaoPic>> requestPics();
}
