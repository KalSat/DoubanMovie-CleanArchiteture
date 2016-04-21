package com.tritiger.doubanmovie.data.entity.mapper;

import com.tritiger.doubanmovie.data.entity.MovieEntity;
import com.tritiger.doubanmovie.domain.Movie;

import java.util.ArrayList;

/**
 * Mapper class used to transform {@link MovieEntity} (in the data layer) to {@link Movie} in the
 * domain layer.
 */
public class MovieEntityMapper {

    /**
     * Transform a {@link MovieEntity} into an {@link Movie}.
     *
     * @param movieEntity Object to be transformed.
     * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
     */
    public static Movie transform(MovieEntity movieEntity) {
        Movie movie = null;
        if (movieEntity != null) {
            movie = new Movie(movieEntity.id);
            movie.title = movieEntity.title;
            if (movieEntity.rating != null) {
                movie.rating = movieEntity.rating.average;
            }
            if (movieEntity.images != null) {
                movie.coverUrlSmall = movieEntity.images.small;
                movie.coverUrlMedium = movieEntity.images.medium;
                movie.coverUrlLarge = movieEntity.images.large;
            }
        }
        return movie;
    }

    /**
     * Transform a List of {@link MovieEntity} into a List of {@link Movie}.
     *
     * @param movieEntities Object List to be transformed.
     * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
     */
    public static ArrayList<Movie> transform(ArrayList<MovieEntity> movieEntities) {
        ArrayList<Movie> movies;
        if (movieEntities != null && !movieEntities.isEmpty()) {
            movies = new ArrayList<>(movieEntities.size());
            for (final MovieEntity movieEntity : movieEntities) {
                movies.add(transform(movieEntity));
            }
        } else {
            movies = new ArrayList<>();
        }
        return movies;
    }
}
