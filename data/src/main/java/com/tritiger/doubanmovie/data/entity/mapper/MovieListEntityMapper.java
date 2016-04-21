package com.tritiger.doubanmovie.data.entity.mapper;

import com.tritiger.doubanmovie.data.entity.MovieListEntity;
import com.tritiger.doubanmovie.domain.MovieList;

/**
 * Mapper class used to transform {@link MovieListEntity} (in the data layer)
 * to {@link MovieList} in the domain layer.
 */
public class MovieListEntityMapper {

    /**
     * Transform a {@link MovieListEntity} into an {@link MovieList}.
     *
     * @param movieListEntity Object to be transformed.
     * @return {@link MovieList} if valid {@link MovieListEntity} otherwise null.
     */
    public static MovieList transform(MovieListEntity movieListEntity) {
        MovieList movieList = null;
        if (movieListEntity != null) {
            movieList = new MovieList();
            movieList.title = movieListEntity.title;
            movieList.total = movieListEntity.total;
            movieList.start = movieListEntity.start;
            movieList.count = movieListEntity.count;
            movieList.movies = MovieEntityMapper.transform(movieListEntity.subjects);
        }
        return movieList;
    }
}
