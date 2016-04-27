package com.tritiger.doubanmovie.data.net;

import com.tritiger.doubanmovie.data.entity.MovieEntity;
import com.tritiger.doubanmovie.data.entity.MovieListEntity;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Api for retrieving data from the network.
 */
public interface DoubanApi {

    String BASE_URL = "https://api.douban.com/v2/";
    String API_KEY = "0b2bdeda43b5688921839c8ecb20399b";

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link MovieEntity}.
     */
    @Headers("Cache-Control: public, max-age=1800")
    @GET("movie/top250")
    Observable<MovieListEntity> getTopMovies(@Query("start") int start,
                                             @Query("count") int count,
                                             @Query("apikey") String key);

    @Headers("Cache-Control: public, only-if-cached, max-stale=2419200")
    @GET("movie/top250")
    Observable<MovieListEntity> getTopMoviesFromCache(@Query("start") int start,
                                                      @Query("count") int count,
                                                      @Query("apikey") String key);

    @Headers("Cache-Control: public, max-age=1800")
    @GET("movie/subject/{id}")
    Observable<MovieEntity> getMovie(@Path("id") String id,
                                     @Query("apikey") String key);

    @Headers("Cache-Control: public, only-if-cached, max-stale=2419200")
    @GET("movie/subject/{id}")
    Observable<MovieEntity> getMovieFromCache(@Path("id") String id,
                                              @Query("apikey") String key);
}
