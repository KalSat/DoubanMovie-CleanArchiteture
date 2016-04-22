package com.tritiger.doubanmovie.domain;

import java.io.Serializable;

/**
 * Class that represents a Movie in the domain layer.
 */
public class Movie implements Serializable {

    public final String id;

    public String alt;
    public String title;
    public String originalTitle;
    public Image cover;
    public Rating rating;
    public int ratingNum;
    public String year;
    public Cast[] directors;
    public Cast[] writers;
    public Cast[] casts;
    public String[] types;
    public String[] countries;
    public String[] languages;
    public String[] releaseDates;
    public String[] durations;
    public String[] akas;

    public Movie(String id) {
        this.id = id;
    }

    public static class Cast implements Serializable {
        public String alt;
        public String id;
        public String name;
        public String englishName;
        public Image avatar;
    }

    public static class Rating implements Serializable {
        public int max;
        public float average;
        public int min;
        public int[] details;
        public String star;
    }

    public static class Image implements Serializable {
        public String small;
        public String large;
        public String medium;
    }
}
