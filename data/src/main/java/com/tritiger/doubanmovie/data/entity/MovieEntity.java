package com.tritiger.doubanmovie.data.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Movie Entity used in the data layer.
 */
public class MovieEntity {

    public String alt;
    public String id;
    public String title;
    public String original_title;
    public String summary;
    public ImageEntity images;
    public RatingEntity rating;
    public int ratings_count;
    public String year;
    public CastEntity[] directors;
    public CastEntity[] writers;
    public CastEntity[] casts;
    public String[] genres;
    public String[] countries;
    public String[] languages;
    public String[] pubdates;
    public String[] durations;
    public String[] aka;

    public class CastEntity implements Serializable {
        public String alt;
        public String id;
        public String name;
        public String name_en;
        public ImageEntity avatars;
    }

    public class RatingEntity implements Serializable {
        public int max;
        public float average;
        public int min;
        public HashMap<String, Integer> details;
        public String stars;
    }

    public class ImageEntity implements Serializable {
        public String small;
        public String large;
        public String medium;
    }
}
