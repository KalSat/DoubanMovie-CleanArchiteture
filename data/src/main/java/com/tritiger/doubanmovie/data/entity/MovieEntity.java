package com.tritiger.doubanmovie.data.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Movie Entity used in the data layer.
 */
public class MovieEntity {

    public String id;
    public RatingBean rating;
    public ArrayList<String> genres;
    public int collect_count;
    public ArrayList<CastBean> casts;
    public String title;
    public String original_title;
    public String subtype;
    public ArrayList<DirectorBean> directors;
    public String year;
    public ImagesBean images;
    public String alt;

    public class CastBean implements Serializable {
        public String alt;
        public String id;
        public String name;
        public ImagesBean avatars;
    }

    public class RatingBean implements Serializable {
        public int max;
        public float average;
        public int min;
        public String stars;
    }

    public static class DirectorBean implements Serializable {
        public String alt;
        public String id;
        public String name;
        public ImagesBean avatars;
    }

    public class ImagesBean implements Serializable {
        public String small;
        public String large;
        public String medium;
    }
}
