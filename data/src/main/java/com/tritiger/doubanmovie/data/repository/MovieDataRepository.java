package com.tritiger.doubanmovie.data.repository;

import com.tritiger.doubanmovie.data.entity.MovieEntity;
import com.tritiger.doubanmovie.data.entity.MovieListEntity;
import com.tritiger.doubanmovie.data.entity.mapper.MovieEntityMapper;
import com.tritiger.doubanmovie.data.entity.mapper.MovieListEntityMapper;
import com.tritiger.doubanmovie.data.repository.datasource.MovieDataStore;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.domain.MovieList;
import com.tritiger.doubanmovie.domain.repository.MovieRepository;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link MovieRepository} for retrieving user data.
 */
public class MovieDataRepository implements MovieRepository {

    private final MovieDataStore userDataStore;

    public MovieDataRepository(MovieDataStore userDataStore) {
        this.userDataStore = userDataStore;
    }

    @Override
    public Observable<MovieList> topMovies(int start, int count) {
        return userDataStore.getTopMovieList(start, count)
                .map(new Func1<MovieListEntity, MovieList>() {
                    @Override
                    public MovieList call(MovieListEntity movieListEntity) {
                        return MovieListEntityMapper.transform(movieListEntity);
                    }
                });
    }

    @Override
    public Observable<Movie> movie(String id) {
        return userDataStore.getMovie(id)
                .map(new Func1<MovieEntity, Movie>() {
                    @Override
                    public Movie call(MovieEntity movieEntity) {
                        return MovieEntityMapper.transform(movieEntity);
                    }
                });
    }
}
