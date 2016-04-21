package com.tritiger.doubanmovie.domain.repository;

import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.domain.MovieList;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Movie} related data.
 */
public interface MovieRepository {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Movie}.
     */
    Observable<MovieList> topMovies(int start, int count);

}
