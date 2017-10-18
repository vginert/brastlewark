package com.vginert.brastlewark.data.repository;

import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.entity.mapper.HeroEntityDataMapper;
import com.vginert.brastlewark.data.repository.datasource.HeroDataStoreFactory;
import com.vginert.brastlewark.data.repository.datasource.IHeroDataStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HeroRepositoryTest {

    private static final int FAKE_HERO_ID = 123;
    private static final String FAKE_NAME_FILTER = "Jon Snow";
    private static final Integer FAKE_MIN_AGE_FILTER = 1;
    private static final Integer FAKE_MAX_AGE_FILTER = 5;
    private static final Integer FAKE_MIN_WEIGHT_FILTER = 10;
    private static final Integer FAKE_MAX_WEIGHT_FILTER = 15;
    private static final Integer FAKE_MIN_HEIGHT_FILTER = 20;
    private static final Integer FAKE_MAX_HEIGHT_FILTER = 25;

    private HeroRepository heroRepository;

    @Mock
    private HeroDataStoreFactory mockHeroDataStoreFactory;
    @Mock
    private HeroEntityDataMapper mockHeroEntityDataMapper;
    @Mock
    private IHeroDataStore mockHeroDataStore;
    @Mock
    private HeroEntity mockHeroEntity;

    @Before
    public void setUp() {
        heroRepository = new HeroRepository(mockHeroDataStoreFactory, mockHeroEntityDataMapper);
        given(mockHeroDataStoreFactory.create(anyInt())).willReturn(mockHeroDataStore);
        given(mockHeroDataStoreFactory.createCloudDataStore()).willReturn(mockHeroDataStore);
    }

    @Test
    public void testGetHeroesEmptyFilterHappyCase() {
        List<HeroEntity> heroesList = new ArrayList<>();
        heroesList.add(mockHeroEntity);
        given(mockHeroDataStore.heroEntityList(null, null, null, null, null, null, null)).willReturn(Observable.just(heroesList));

        heroRepository.heroes(null, null, null, null, null, null, null);

        verify(mockHeroDataStoreFactory).createCloudDataStore();
        verify(mockHeroDataStore).heroEntityList(null, null, null, null, null, null, null);
    }

    @Test
    public void testGetHeroesFullFilterHappyCase() {
        List<HeroEntity> heroesList = new ArrayList<>();
        heroesList.add(mockHeroEntity);
        given(mockHeroDataStore.heroEntityList(FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER, FAKE_MAX_AGE_FILTER,
                FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER)).willReturn(Observable.just(heroesList));

        heroRepository.heroes(FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER, FAKE_MAX_AGE_FILTER,
                FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER);

        verify(mockHeroDataStoreFactory).createCloudDataStore();
        verify(mockHeroDataStore).heroEntityList(FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER, FAKE_MAX_AGE_FILTER,
                FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER);
    }

    @Test
    public void testGetHeroHappyCase() {
        HeroEntity heroEntity = new HeroEntity();
        given(mockHeroDataStore.heroEntityDetails(FAKE_HERO_ID)).willReturn(Observable.just(heroEntity));
        heroRepository.hero(FAKE_HERO_ID);

        verify(mockHeroDataStoreFactory).create(FAKE_HERO_ID);
        verify(mockHeroDataStore).heroEntityDetails(FAKE_HERO_ID);
    }
}