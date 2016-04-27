package com.tritiger.doubanmovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.widget.HiddenTextView;

public class MovieDetailFragment extends AbstractFragment {

    private static final String ARG_MOVIE = "movie";

    private Movie movie;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        }
    }

    @Override
    protected View onChildCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        getActivity().setTitle(movie.title);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.md_swipe);

        if (movie.cover != null) {
            Picasso.with(getContext()).load(movie.cover.large)
                    .into((ImageView) view.findViewById(R.id.md_cover));
        }

//        rbSubjectRating = (RatingBar) findViewById(R.id.rb_subject_rating);
//        tvSubjectRating = (TextView) findViewById(R.id.tv_subject_rating);
//        tvSubjectFavoriteCount = (TextView) findViewById(R.id.tv_subject_favorite_count);

        ((HiddenTextView) view.findViewById(R.id.md_original_title)).setNullableText(
                !movie.originalTitle.equals(movie.title) ? movie.originalTitle : null);
        ((HiddenTextView) view.findViewById(R.id.md_director)).setNullableText(combineNames(movie.directors));
        ((HiddenTextView) view.findViewById(R.id.md_writers)).setNullableText(combineNames(movie.writers));
        ((HiddenTextView) view.findViewById(R.id.md_actors)).setNullableText(combineNames(movie.casts));
        ((HiddenTextView) view.findViewById(R.id.md_type)).setNullableText(combineString(movie.types));
        ((HiddenTextView) view.findViewById(R.id.md_country)).setNullableText(combineString(movie.countries));
        ((HiddenTextView) view.findViewById(R.id.md_language)).setNullableText(combineString(movie.languages));
        ((HiddenTextView) view.findViewById(R.id.md_release_date)).setNullableText(combineString(movie.releaseDates));
        ((HiddenTextView) view.findViewById(R.id.md_runtime)).setNullableText(combineString(movie.durations));
        ((HiddenTextView) view.findViewById(R.id.md_aka)).setNullableText(combineString(movie.akas));
        ((TextView) view.findViewById(R.id.md_summary)).setText("");

        view.findViewById(R.id.md_summary_more).setOnClickListener(null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.md_recommend_movies);

    }

    private String combineString(String[] strings) {
        StringBuilder builder = new StringBuilder();

        if (strings != null) {
            for (String string : strings) {
                builder.append(string);
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }

    private String combineNames(Movie.Cast[] casts) {
        StringBuilder builder = new StringBuilder();

        if (casts != null) {
            for (Movie.Cast cast : casts) {
                builder.append(cast.name);
                builder.append(" / ");
            }
            builder.delete(builder.length() - 3, builder.length());
        }
        return builder.toString();
    }

    @Override
    protected void childLoadData() {

    }


}
