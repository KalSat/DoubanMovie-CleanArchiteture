package com.tritiger.doubanmovie.data.entity;

import java.util.ArrayList;

/**
 * Movie list Entity used in the data layer.
 */
public class MovieListEntity {
    public String title;
    public int total;
    public int start;
    public int count;
    public ArrayList<MovieEntity> subjects;
}
