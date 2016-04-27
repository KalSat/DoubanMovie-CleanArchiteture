package com.tritiger.doubanmovie.domain.interactor;

import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.domain.executor.PostExecutionThread;
import com.tritiger.doubanmovie.domain.executor.ThreadExecutor;
import com.tritiger.doubanmovie.domain.repository.MovieRepository;

import rx.Subscriber;

/**
 * Created by chengbiao on 2016/4/27.
 */
public class GetMovie extends UseCase {

    private final MovieRepository movieRepository;

    public GetMovie(MovieRepository movieRepository,
                    ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.movieRepository = movieRepository;
    }

    public void execute(String id, Subscriber<Movie> useCaseSubscriber) {
        super.execute(movieRepository.movie(id), useCaseSubscriber);
    }
}
