package com.tritiger.doubanmovie.data.repository.datasource;

import com.tritiger.doubanmovie.data.entity.MovieEntity;
import com.tritiger.doubanmovie.data.entity.MovieListEntity;

import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface MovieDataStore {
    /**
     * Get an {@link Observable} which will emit a List of {@link MovieEntity}.
     */
    Observable<MovieListEntity> getTopMovieList(int start, int count);

    /**
     * Get an {@link Observable} which will emit a {@link MovieEntity}.
     */
    Observable<MovieEntity> getMovie(String id);

}
