package com.tritiger.doubanmovie.data.repository;

import com.tritiger.doubanmovie.data.entity.MovieListEntity;
import com.tritiger.doubanmovie.data.entity.mapper.MovieListEntityMapper;
import com.tritiger.doubanmovie.data.repository.datasource.MovieDataStore;
import com.tritiger.doubanmovie.data.repository.datasource.MovieDataStoreFactory;
import com.tritiger.doubanmovie.domain.MovieList;
import com.tritiger.doubanmovie.domain.repository.MovieRepository;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link MovieRepository} for retrieving user data.
 */
public class MovieDataRepository implements MovieRepository {

    private final MovieDataStoreFactory mMovieDataStoreFactory;

    /**
     * Constructs a {@link MovieRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     */
    public MovieDataRepository(MovieDataStoreFactory dataStoreFactory) {
        this.mMovieDataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<MovieList> topMovies(int start, int count) {
        //we always get all users from the cloud
        final MovieDataStore userDataStore = this.mMovieDataStoreFactory.createCloudDataStore();

        return userDataStore.getTopMovieList(start, count)
                .map(new Func1<MovieListEntity, MovieList>() {
                    @Override
                    public MovieList call(MovieListEntity movieListEntity) {
                        return MovieListEntityMapper.transform(movieListEntity);
                    }
                });
    }
}
