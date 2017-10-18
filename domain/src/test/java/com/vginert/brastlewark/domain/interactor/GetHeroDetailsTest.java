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
public class GetHeroDetailsTest {

    private static final int FAKE_HERO_ID = 123;

    private GetHeroDetails getHeroDetails;

    @Mock
    private IHeroRepository mockHeroRepository;
    @Mock
    private IThreadExecutor mockThreadExecutor;
    @Mock
    private IPostExecutionThread mockPostExecutionThread;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        getHeroDetails = new GetHeroDetails(mockHeroRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetHeroDetailsUseCaseObservableHappyCase() {
        getHeroDetails.buildUseCaseObservable(GetHeroDetails.Params.forHero(FAKE_HERO_ID));

        verify(mockHeroRepository).hero(FAKE_HERO_ID);
        verifyNoMoreInteractions(mockHeroRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        getHeroDetails.buildUseCaseObservable(null);
    }
}