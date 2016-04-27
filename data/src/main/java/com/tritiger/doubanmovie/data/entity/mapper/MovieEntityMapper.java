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
            movie.alt = movieEntity.alt;
            movie.title = movieEntity.title;
            movie.originalTitle = movieEntity.original_title;
            movie.summary = movieEntity.summary;
            movie.cover = transform(movieEntity.images);
            movie.rating = transform(movieEntity.rating);
            movie.ratingNum = movieEntity.ratings_count;
            movie.year = movieEntity.year;
            if (movieEntity.directors != null) {
                movie.directors = new Movie.Cast[movieEntity.directors.length];
                for (int i = 0; i < movieEntity.directors.length; i++) {
                    movie.directors[i] = transform(movieEntity.directors[i]);
                }
            }
            if (movieEntity.writers != null) {
                movie.writers = new Movie.Cast[movieEntity.writers.length];
                for (int i = 0; i < movieEntity.writers.length; i++) {
                    movie.writers[i] = transform(movieEntity.writers[i]);
                }
            }
            if (movieEntity.casts != null) {
                movie.casts = new Movie.Cast[movieEntity.casts.length];
                for (int i = 0; i < movieEntity.casts.length; i++) {
                    movie.casts[i] = transform(movieEntity.casts[i]);
                }
            }
            if (movieEntity.genres != null) {
                movie.types = new String[movieEntity.genres.length];
                System.arraycopy(movieEntity.genres, 0, movie.types, 0, movieEntity.genres.length);
            }
            if (movieEntity.countries != null) {
                movie.countries = new String[movieEntity.countries.length];
                System.arraycopy(movieEntity.countries, 0, movie.countries, 0,
                        movieEntity.countries.length);
            }
            if (movieEntity.languages != null) {
                movie.languages = new String[movieEntity.languages.length];
                System.arraycopy(movieEntity.languages, 0, movie.languages, 0,
                        movieEntity.languages.length);
            }
            if (movieEntity.pubdates != null) {
                movie.releaseDates = new String[movieEntity.pubdates.length];
                System.arraycopy(movieEntity.pubdates, 0, movie.releaseDates, 0,
                        movieEntity.pubdates.length);
            }
            if (movieEntity.durations != null) {
                movie.durations = new String[movieEntity.durations.length];
                System.arraycopy(movieEntity.durations, 0, movie.durations, 0,
                        movieEntity.durations.length);
            }
            if (movieEntity.aka != null) {
                movie.akas = new String[movieEntity.aka.length];
                System.arraycopy(movieEntity.aka, 0, movie.akas, 0, movieEntity.aka.length);
            }
        }
        return movie;
    }

    private static Movie.Cast transform(MovieEntity.CastEntity castEntity) {
        Movie.Cast cast = null;
        if (castEntity != null) {
            cast = new Movie.Cast();
            cast.alt = castEntity.alt;
            cast.id = castEntity.id;
            cast.name = castEntity.name;
            cast.englishName = castEntity.name_en;
            cast.avatar = transform(castEntity.avatars);
        }
        return cast;
    }

    private static Movie.Rating transform(MovieEntity.RatingEntity ratingEntity) {
        Movie.Rating rating = null;
        if (ratingEntity != null) {
            rating = new Movie.Rating();
            rating.max = ratingEntity.max;
            rating.average = ratingEntity.average;
            rating.min = ratingEntity.min;
            if (ratingEntity.details != null) {
                rating.details = new int[ratingEntity.details.size() + 1];
                rating.details[1] = ratingEntity.details.get("1");
                rating.details[2] = ratingEntity.details.get("2");
                rating.details[3] = ratingEntity.details.get("3");
                rating.details[4] = ratingEntity.details.get("4");
                rating.details[5] = ratingEntity.details.get("5");
            }
            rating.star = ratingEntity.stars;
        }
        return rating;
    }

    private static Movie.Image transform(MovieEntity.ImageEntity imageEntity) {
        Movie.Image image = null;
        if (imageEntity != null) {
            image = new Movie.Image();
            image.small = imageEntity.small;
            image.large = imageEntity.large;
            image.medium = imageEntity.medium;
        }
        return image;
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
