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
import com.tritiger.doubanmovie.presenter.MovieDetailPresenter;
import com.tritiger.doubanmovie.ui.IViewMovieDetail;
import com.tritiger.doubanmovie.widget.HiddenTextView;

public class MovieDetailFragment extends AbstractFragment implements IViewMovieDetail {

    private static final String ARG_MOVIE = "movie";

    private Movie movie;
    private MovieDetailPresenter movieDetailPresenter;

    private HiddenTextView originalTitleText;
    private HiddenTextView directorText;
    private HiddenTextView writerText;
    private HiddenTextView actorText;
    private HiddenTextView typeText;
    private HiddenTextView countryText;
    private HiddenTextView languageText;
    private HiddenTextView releaseDateText;
    private HiddenTextView runtimeText;
    private HiddenTextView akaText;
    private TextView summaryText;

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
        movieDetailPresenter = new MovieDetailPresenter(this);

        return view;
    }

    @Override
    protected void childLoadData() {
        movieDetailPresenter.refreshMovie(movie.id);
    }

    @Override
    public void onResume() {
        super.onResume();
        movieDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        movieDetailPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        movieDetailPresenter.destroy();
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.md_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieDetailPresenter.refreshMovie(movie.id);
            }
        });

        originalTitleText = (HiddenTextView) view.findViewById(R.id.md_original_title);
        directorText = (HiddenTextView) view.findViewById(R.id.md_director);
        writerText = (HiddenTextView) view.findViewById(R.id.md_writers);
        actorText = (HiddenTextView) view.findViewById(R.id.md_actors);
        typeText = (HiddenTextView) view.findViewById(R.id.md_type);
        countryText = (HiddenTextView) view.findViewById(R.id.md_country);
        languageText = (HiddenTextView) view.findViewById(R.id.md_language);
        releaseDateText = (HiddenTextView) view.findViewById(R.id.md_release_date);
        runtimeText = (HiddenTextView) view.findViewById(R.id.md_runtime);
        akaText = (HiddenTextView) view.findViewById(R.id.md_aka);
        summaryText = (TextView) view.findViewById(R.id.md_summary);

        if (movie.cover != null) {
            Picasso.with(getContext()).load(movie.cover.large)
                    .into((ImageView) view.findViewById(R.id.md_cover));
        }

//        rbSubjectRating = (RatingBar) findViewById(R.id.rb_subject_rating);
//        tvSubjectRating = (TextView) findViewById(R.id.tv_subject_rating);
//        tvSubjectFavoriteCount = (TextView) findViewById(R.id.tv_subject_favorite_count);

        updateView(this.movie);

        view.findViewById(R.id.md_summary_more).setOnClickListener(null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.md_recommend_movies);

    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void renderMovie(Movie movie) {
        this.movie = movie;
        updateView(movie);
    }

    private void updateView(Movie movie) {
        originalTitleText.setNullableText(
                !movie.originalTitle.equals(movie.title) ? movie.originalTitle : null);
        directorText.setNullableText(combineNames(movie.directors));
        writerText.setNullableText(combineNames(movie.writers));
        actorText.setNullableText(combineNames(movie.casts));
        typeText.setNullableText(combineString(movie.types));
        countryText.setNullableText(combineString(movie.countries));
        languageText.setNullableText(combineString(movie.languages));
        releaseDateText.setNullableText(combineString(movie.releaseDates));
        runtimeText.setNullableText(combineString(movie.durations));
        akaText.setNullableText(combineString(movie.akas));
        summaryText.setText(movie.summary);
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
}
