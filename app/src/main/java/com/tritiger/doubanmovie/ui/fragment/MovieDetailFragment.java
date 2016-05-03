package com.tritiger.doubanmovie.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.databinding.MovieDetailBinding;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.presenter.MovieDetailViewModel;
import com.tritiger.doubanmovie.ui.IViewMovieDetail;

public class MovieDetailFragment extends AbstractFragment implements IViewMovieDetail {

    private static final String ARG_MOVIE = "movie";

    private MovieDetailBinding binding;
    private MovieDetailViewModel viewModel;

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
    protected View onChildCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_movie_detail, container, false);
        binding.setV(this);

        Movie movie = null;
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        }

        if (movie != null) {
            getActivity().setTitle(movie.title);
        }

        viewModel = new MovieDetailViewModel(this);
        binding.setVm(viewModel);
        viewModel.movie.set(movie);

        initView();

        return binding.getRoot();
    }

    @Override
    protected void childLoadData() {
        viewModel.refreshMovie();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.destroy();
    }

    private void initView() {
        mSwipeRefreshLayout = binding.swipe;
    }

    @SuppressWarnings("unused")
    public void onRefresh() {
        viewModel.refreshMovie();
    }

    @SuppressWarnings("unused")
    public void onMoreClick(View v) {
        if (binding.summary.getMaxLines() > 5) {
            binding.summary.setMaxLines(5);
            binding.summaryMore.setText(getString(R.string.more_info));
        } else {
            binding.summary.setMaxLines(Integer.MAX_VALUE);
            binding.summaryMore.setText(getString(R.string.put_away));
        }
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

    public static String combineString(String[] strings) {
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

    public static String combineNames(Movie.Cast[] casts) {
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
