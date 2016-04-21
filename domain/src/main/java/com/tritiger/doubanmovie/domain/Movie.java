package com.tritiger.doubanmovie.domain;

/**
 * Class that represents a Movie in the domain layer.
 */
public class Movie {

    public final String id;

    public String title;
    public String originalTitle;
    public String year;
    public String subtype;
    public String alt;
    public float rating;

    public String coverUrlSmall;
    public String coverUrlMedium;
    public String coverUrlLarge;

//    public ArrayList<String> genres;
//    public int collect_count;
//    public ArrayList<CastBean> casts;
//    public ArrayList<DirectorBean> directors;
//    public ImagesBean images;

    public Movie(String id) {
        this.id = id;
    }
}
