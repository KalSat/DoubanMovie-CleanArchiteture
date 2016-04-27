package com.tritiger.doubanmovie.ui;

import com.tritiger.doubanmovie.domain.Movie;

/**
 * Created by chengbiao on 2016/4/27.
 */
public interface IViewMovieDetail extends IViewLoadData {

    void renderMovie(Movie movie);
}
