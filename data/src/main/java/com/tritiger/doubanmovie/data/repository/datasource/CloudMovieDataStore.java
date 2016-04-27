package com.tritiger.doubanmovie.data.repository.datasource;

import com.tritiger.doubanmovie.data.entity.MovieEntity;
import com.tritiger.doubanmovie.data.entity.MovieListEntity;
import com.tritiger.doubanmovie.data.net.NetworkManager;

import rx.Observable;

/**
 * {@link MovieDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudMovieDataStore implements MovieDataStore {

    private final NetworkManager network;

    /**
     * Construct a {@link MovieDataStore} based on connections to the api (Cloud).
     *
     * @param network The {@link NetworkManager} implementation to use.
     */
    public CloudMovieDataStore(NetworkManager network) {
        this.network = network;
    }

    @Override
    public Observable<MovieListEntity> getTopMovieList(int start, int count) {
        return this.network.getTopMovies(start, count);
    }

    @Override
    public Observable<MovieEntity> getMovie(String id) {
        return this.network.getMovie(id);
    }
}
