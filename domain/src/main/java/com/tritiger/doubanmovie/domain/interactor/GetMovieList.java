package com.tritiger.doubanmovie.domain.interactor;

import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.domain.MovieList;
import com.tritiger.doubanmovie.domain.executor.PostExecutionThread;
import com.tritiger.doubanmovie.domain.executor.ThreadExecutor;
import com.tritiger.doubanmovie.domain.repository.MovieRepository;

import rx.Subscriber;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Movie}.
 */
public class GetMovieList extends UseCase {

    private final MovieRepository movieRepository;

    public GetMovieList(MovieRepository movieRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.movieRepository = movieRepository;
    }

    public void execute(int start, int count, Subscriber<MovieList> useCaseSubscriber) {
        super.execute(movieRepository.topMovies(start, count), useCaseSubscriber);
    }
}
