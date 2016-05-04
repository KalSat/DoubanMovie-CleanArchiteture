package com.tritiger.doubanmovie.presentation.view;

import com.tritiger.doubanmovie.domain.Movie;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link Movie}.
 */
public interface IViewMovieGrid extends IViewLoadData {
    /**
     * Render a user list in the UI.
     *
     * @param MovieCollection The collection of {@link Movie} that will be shown.
     * @param total
     */
    void resetMovieList(List<Movie> MovieCollection, int total);

    void addMovieList(List<Movie> MovieCollection, int total);

    void showLoadMoreComplete();

    void showLoadMoreFail();

    /**
     * View a {@link Movie} profile/details.
     *
     * @param Movie The user that will be shown.
     */
    void viewMovie(Movie Movie);
}
