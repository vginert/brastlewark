package com.vginert.brastlewark.android.presenter;

import android.content.Context;

import com.vginert.brastlewark.android.model.mapper.HeroModelDataMapper;
import com.vginert.brastlewark.android.view.HeroDetailsView;
import com.vginert.brastlewark.domain.interactor.GetHeroDetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HeroDetailsPresenterTest {

    private static final int HERO_ID = 1;

    private HeroDetailsPresenter heroDetailsPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private HeroDetailsView mockHeroDetailsView;
    @Mock
    private GetHeroDetails mockGetHeroDetails;
    @Mock
    private HeroModelDataMapper mockHeroModelDataMapper;

    @Before
    public void setUp() {
        heroDetailsPresenter = new HeroDetailsPresenter(mockGetHeroDetails, mockHeroModelDataMapper);
        heroDetailsPresenter.setView(mockHeroDetailsView);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHeroDetailsPresenterInitialize() {
        given(mockHeroDetailsView.context()).willReturn(mockContext);
        heroDetailsPresenter.initialize(HERO_ID);
        verify(mockGetHeroDetails).execute(any(DisposableObserver.class), any(GetHeroDetails.Params.class));
    }
}