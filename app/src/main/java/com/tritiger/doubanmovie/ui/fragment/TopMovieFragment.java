package com.tritiger.doubanmovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.presentation.view.IViewMovieGrid;
import com.tritiger.doubanmovie.presentation.viewmodel.MovieGridViewModel;
import com.tritiger.doubanmovie.ui.activity.MovieDetailActivity;
import com.tritiger.doubanmovie.ui.adapter.MovieGridAdapter;
import com.tritiger.doubanmovie.widget.EndlessRecyclerView;

import java.util.List;

/**
 * Fragment for showing top movies.
 */
public class TopMovieFragment extends AbstractFragment implements IViewMovieGrid {

    public static final int LOAD_DATA_COUNT = 20;

    private MovieGridViewModel movieGridPresenter;
    private MovieGridAdapter adapter;
    private EndlessRecyclerView recyclerView;


    public TopMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onChildCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_movie, container, false);
        initView(view);
        movieGridPresenter = new MovieGridViewModel(this);
        return view;
    }

    @Override
    protected void childLoadData() {
        movieGridPresenter.loadMovieList(LOAD_DATA_COUNT);
    }

    @Override
    public void onResume() {
        super.onResume();
        movieGridPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        movieGridPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        movieGridPresenter.destroy();
    }

    private void initView(View view) {
        recyclerView = (EndlessRecyclerView) view.findViewById(R.id.mv_recycler);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mv_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                movieGridPresenter.refreshMovieList(LOAD_DATA_COUNT);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setOnLoadMoreListener(onLoadMoreListener);

        adapter = new MovieGridAdapter(getContext(), recyclerView);
        adapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);
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
    public void resetMovieList(List<Movie> movieList, int total) {
        adapter.maxDataCount = total;
        adapter.set(movieList);
    }

    @Override
    public void addMovieList(List<Movie> movieList, int total) {
        adapter.maxDataCount = total;
        adapter.add(movieList);
    }

    @Override
    public void viewMovie(Movie movie) {
        MovieDetailActivity.startActivity(getContext(), movie);
    }

    @Override
    public void showLoadMoreComplete() {
        recyclerView.loadingComplete();
    }

    @Override
    public void showLoadMoreFail() {
        recyclerView.loadingFail();
    }

    private final EndlessRecyclerView.OnLoadMoreListener onLoadMoreListener =
            new EndlessRecyclerView.OnLoadMoreListener() {
                @Override
                public void onLoadMore(int currentSize) {
                    movieGridPresenter.fetchMoreMovieData(currentSize, LOAD_DATA_COUNT);
                }
            };

    private final MovieGridAdapter.OnItemClickListener itemClickListener =
            new MovieGridAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Movie movie) {
                    movieGridPresenter.onMovieClicked(movie);
                }
            };
}
