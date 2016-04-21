package com.tritiger.doubanmovie.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tritiger.doubanmovie.UIThread;
import com.tritiger.doubanmovie.data.executor.JobExecutor;
import com.tritiger.doubanmovie.data.repository.MovieDataRepository;
import com.tritiger.doubanmovie.data.repository.datasource.MovieDataStoreFactory;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.domain.MovieList;
import com.tritiger.doubanmovie.domain.exception.DefaultErrorBundle;
import com.tritiger.doubanmovie.domain.exception.ErrorBundle;
import com.tritiger.doubanmovie.domain.interactor.DefaultSubscriber;
import com.tritiger.doubanmovie.domain.interactor.GetMovieList;
import com.tritiger.doubanmovie.exception.ErrorMessageFactory;
import com.tritiger.doubanmovie.ui.IViewMovieGrid;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class MovieGridPresenter implements Presenter {

    private final IViewMovieGrid viewMovieGrid;
    private final GetMovieList getMovieListCase;
    private final GetMovieList getMovieListCacheCase;

    public MovieGridPresenter(@NonNull Context context, @NonNull IViewMovieGrid viewMovieGrid) {
        MovieDataStoreFactory factory = new MovieDataStoreFactory(context);
        this.getMovieListCase = new GetMovieList(
                new MovieDataRepository(factory.createCloudDataStore()),
                new JobExecutor(), new UIThread());
        this.getMovieListCacheCase = new GetMovieList(
                new MovieDataRepository(factory.createCacheDataStore()),
                new JobExecutor(), new UIThread());
        this.viewMovieGrid = viewMovieGrid;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        getMovieListCase.unsubscribe();
    }

    /**
     * Reloads all movies.
     */
    public void refreshMovieList(int loadDataCount) {
        hideViewRetry();
        showViewLoading();
        refetchMovieData(loadDataCount);
    }

    public void onMovieClicked(Movie movie) {
        viewMovieGrid.viewMovie(movie);
    }

    private void showViewLoading() {
        viewMovieGrid.showLoading();
    }

    private void hideViewLoading() {
        viewMovieGrid.hideLoading();
    }

    private void showViewRetry() {
        viewMovieGrid.showRetry();
    }

    private void hideViewRetry() {
        viewMovieGrid.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(viewMovieGrid.getContext(),
                errorBundle.getException());
        viewMovieGrid.showError(errorMessage);
    }

    private void refetchMovieData(int count) {
        getMovieListCase.execute(0, count, new DefaultSubscriber<MovieList>() {
            @Override
            public void onNext(MovieList movieList) {
                viewMovieGrid.resetMovieList(movieList.movies, movieList.total);
            }

            @Override
            public void onCompleted() {
                hideViewLoading();
            }

            @Override
            public void onError(Throwable e) {
                hideViewLoading();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
                showViewRetry();
            }
        });
    }

    public void loadMovieList(int count) {
        getMovieListCacheCase.execute(0, count, new DefaultSubscriber<MovieList>() {
            @Override
            public void onNext(MovieList movieList) {
                viewMovieGrid.resetMovieList(movieList.movies, movieList.total);
            }
        });
        refetchMovieData(count);
    }

    public void fetchMoreMovieData(int start, int count) {
        getMovieListCase.execute(start, count, new DefaultSubscriber<MovieList>() {
            @Override
            public void onNext(MovieList movieList) {
                viewMovieGrid.addMovieList(movieList.movies, movieList.total);
            }

            @Override
            public void onCompleted() {
                viewMovieGrid.showLoadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                viewMovieGrid.showLoadMoreFail();
            }
        });
    }
}
