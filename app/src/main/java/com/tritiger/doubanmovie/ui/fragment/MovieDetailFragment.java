package com.tritiger.doubanmovie.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.domain.Movie;

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
        ((TextView) view.findViewById(R.id.md_director)).setText(movie.directors[0].name);
    }

    @Override
    protected void childLoadData() {

    }

}
