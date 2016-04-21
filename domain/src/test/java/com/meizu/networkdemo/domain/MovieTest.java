package com.meizu.networkdemo.domain;

import com.tritiger.doubanmovie.domain.Movie;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MovieTest {

    private static final String FAKE_MOVIE_ID = "123456";

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie(FAKE_MOVIE_ID);
    }

    @Test
    public void testMovieConstructorHappyCase() throws Exception {
        assertThat(movie.id, is(FAKE_MOVIE_ID));
    }
}