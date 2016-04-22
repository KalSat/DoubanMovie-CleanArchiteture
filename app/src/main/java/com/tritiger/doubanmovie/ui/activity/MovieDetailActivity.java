package com.tritiger.doubanmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.ui.fragment.MovieDetailFragment;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String PARAM_MOVIE = "movie";

    public Movie movie;

    public static void startActivity(Context context, Movie movie) {
        context.startActivity(new Intent(context, MovieDetailActivity.class)
                .putExtra(PARAM_MOVIE, movie));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = (Movie) getIntent().getSerializableExtra(PARAM_MOVIE);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = MovieDetailFragment.newInstance(movie);
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
