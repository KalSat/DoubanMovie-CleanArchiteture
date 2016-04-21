package com.tritiger.doubanmovie.domain;

import java.util.ArrayList;

/**
 * Class that represents a Movie list in the domain layer.
 */
public class MovieList {

    public String title;
    public int total;
    public int start;
    public int count;
    public ArrayList<Movie> movies;

}
