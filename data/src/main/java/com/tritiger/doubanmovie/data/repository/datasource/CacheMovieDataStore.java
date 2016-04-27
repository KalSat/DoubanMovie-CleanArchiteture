package com.tritiger.doubanmovie.data.repository.datasource;

import com.tritiger.doubanmovie.data.entity.MovieEntity;
import com.tritiger.doubanmovie.data.entity.MovieListEntity;
import com.tritiger.doubanmovie.data.net.NetworkManager;

import rx.Observable;

/**
 * {@link MovieDataStore} implementation based on reads data from cache.
 */
public class CacheMovieDataStore implements MovieDataStore {

    private final NetworkManager network;

    public CacheMovieDataStore(NetworkManager network) {
        this.network = network;
    }

    @Override
    public Observable<MovieListEntity> getTopMovieList(int start, int count) {
        return this.network.getTopMoviesFromCache(start, count);
    }

    @Override
    public Observable<MovieEntity> getMovie(String id) {
        return this.network.getMovieFromCache(id);
    }
}
