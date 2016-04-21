package com.meizu.networkdemo.domain.interactor;

import com.tritiger.doubanmovie.domain.MovieList;
import com.tritiger.doubanmovie.domain.executor.PostExecutionThread;
import com.tritiger.doubanmovie.domain.executor.ThreadExecutor;
import com.tritiger.doubanmovie.domain.interactor.DefaultSubscriber;
import com.tritiger.doubanmovie.domain.interactor.GetMovieList;
import com.tritiger.doubanmovie.domain.repository.MovieRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetMovieListTest {

    private GetMovieList getMovieList;

    @Mock
    private MovieRepository mockMovieRepository;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getMovieList = new GetMovieList(mockMovieRepository,
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testBuildUseCaseObservable() throws Exception {
        getMovieList.execute(0, 20, new DefaultSubscriber<MovieList>());

        verify(mockMovieRepository).topMovies(0, 20);
        verifyNoMoreInteractions(mockMovieRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}