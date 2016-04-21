package com.meizu.networkdemo.domain.interactor;

import com.tritiger.doubanmovie.domain.executor.PostExecutionThread;
import com.tritiger.doubanmovie.domain.executor.ThreadExecutor;
import com.tritiger.doubanmovie.domain.interactor.UseCase;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UseCaseTest {

    private UseCaseTestClass useCase;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testBuildUseCaseObservableReturnCorrectResult() throws Exception {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        TestScheduler testScheduler = new TestScheduler();
        BDDMockito.given(mockPostExecutionThread.getScheduler()).willReturn(testScheduler);

        useCase.execute(testSubscriber);

        assertThat(testSubscriber.getOnNextEvents().size(), is(0));
    }

    @Test
    public void testSubscriptionWhenExecutingUseCase() throws Exception {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        useCase.execute(testSubscriber);
        useCase.unsubscribe();

        MatcherAssert.assertThat(testSubscriber.isUnsubscribed(), is(true));
    }

    private static class UseCaseTestClass extends UseCase {

        protected UseCaseTestClass(ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        protected Observable buildUseCaseObservable() {
            return Observable.empty();
        }

        protected void execute(Subscriber UseCaseSubscriber) throws IllegalStateException {
            super.execute(buildUseCaseObservable(), UseCaseSubscriber);
        }
    }
}