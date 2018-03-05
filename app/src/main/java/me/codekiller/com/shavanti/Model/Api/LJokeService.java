package me.codekiller.com.shavanti.Model.Api;

import java.util.List;

import io.reactivex.Observable;
import me.codekiller.com.shavanti.Model.Bean.LaifudaoJoke;
import retrofit2.http.GET;

/**
 * Created by Lollipop on 2018/2/28.
 */

public interface LJokeService {
    @GET("xiaohua.json")
    Observable<List<LaifudaoJoke>> requestJokes();
}
