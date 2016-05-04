package com.tritiger.doubanmovie.presentation.viewmodel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.tritiger.doubanmovie.data.executor.JobExecutor;
import com.tritiger.doubanmovie.data.repository.MovieDataRepository;
import com.tritiger.doubanmovie.data.repository.datasource.MovieDataStoreFactory;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.domain.exception.DefaultErrorBundle;
import com.tritiger.doubanmovie.domain.exception.ErrorBundle;
import com.tritiger.doubanmovie.domain.interactor.DefaultSubscriber;
import com.tritiger.doubanmovie.domain.interactor.GetMovie;
import com.tritiger.doubanmovie.presentation.UIThread;
import com.tritiger.doubanmovie.presentation.exception.ErrorMessageFactory;
import com.tritiger.doubanmovie.presentation.view.IViewMovieDetail;

/**
 * Created by chengbiao on 2016/4/27.
 */
public class MovieDetailViewModel extends ViewModel {

    private final IViewMovieDetail viewMovieDetail;
    private final GetMovie getMovieCase;

    public final ObservableField<Movie> movie;

    public MovieDetailViewModel(@NonNull IViewMovieDetail viewMovieDetail) {
        MovieDataStoreFactory factory = new MovieDataStoreFactory(viewMovieDetail.getContext());
        getMovieCase = new GetMovie(
                new MovieDataRepository(factory.createCloudDataStore()),
                new JobExecutor(), new UIThread());
        this.viewMovieDetail = viewMovieDetail;
        movie = new ObservableField<>();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }


    /**
     * Reloads all movies.
     */
    public void refreshMovie() {
        hideViewRetry();
        showViewLoading();
        refetchMovieData(movie.get().id);
    }

    private void showViewLoading() {
        viewMovieDetail.showLoading();
    }

    private void hideViewLoading() {
        viewMovieDetail.hideLoading();
    }

    private void showViewRetry() {
        viewMovieDetail.showRetry();
    }

    private void hideViewRetry() {
        viewMovieDetail.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(viewMovieDetail.getContext(),
                errorBundle.getException());
        viewMovieDetail.showError(errorMessage);
    }

    private void refetchMovieData(String id) {
        getMovieCase.execute(id, new DefaultSubscriber<Movie>() {
            @Override
            public void onNext(Movie movie) {
                MovieDetailViewModel.this.movie.set(movie);
            }

            @Override
            public void onCompleted() {
                hideViewLoading();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

                hideViewLoading();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
                showViewRetry();
            }
        });
    }
}
