package com.vginert.brastlewark.domain.interactor;

import com.vginert.brastlewark.domain.executor.IPostExecutionThread;
import com.vginert.brastlewark.domain.executor.IThreadExecutor;
import com.vginert.brastlewark.domain.repository.IHeroRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetHeroListTest {

    private static final String FAKE_NAME_FILTER = "Jon Snow";
    private static final Integer FAKE_MIN_AGE_FILTER = 1;
    private static final Integer FAKE_MAX_AGE_FILTER = 5;
    private static final Integer FAKE_MIN_WEIGHT_FILTER = 10;
    private static final Integer FAKE_MAX_WEIGHT_FILTER = 15;
    private static final Integer FAKE_MIN_HEIGHT_FILTER = 20;
    private static final Integer FAKE_MAX_HEIGHT_FILTER = 25;

    private GetHeroList getHeroList;

    @Mock
    private IThreadExecutor mockThreadExecutor;
    @Mock
    private IPostExecutionThread mockPostExecutionThread;
    @Mock
    private IHeroRepository mockHeroRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        getHeroList = new GetHeroList(mockHeroRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetHeroListUseCaseObservableHappyCase() {
        getHeroList.buildUseCaseObservable(
                GetHeroList.Params.forQuery(FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER, FAKE_MAX_AGE_FILTER,
                        FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER));

        verify(mockHeroRepository).heroes(FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER, FAKE_MAX_AGE_FILTER,
                FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER);
        verifyNoMoreInteractions(mockHeroRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }


    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        getHeroList.buildUseCaseObservable(null);
    }
}