package com.vginert.brastlewark.data.repository.datasource;

import com.vginert.brastlewark.data.cache.HeroCache;
import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.filter.HeroFilter;
import com.vginert.brastlewark.data.net.RestApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudHeroDataStoreTest {

    private static final int FAKE_HERO_ID = 0;

    private CloudHeroDataStore cloudHeroDataStore;

    @Mock
    private RestApi mockRestApi;
    @Mock
    private HeroCache mockHeroCache;
    @Mock
    private HeroFilter mockHeroFilter;


    @Before
    public void setUp() {
        List<HeroEntity> heroEntityList = new ArrayList<>();
        heroEntityList.add(mock(HeroEntity.class));
        Observable<List<HeroEntity>> fakeObservable = Observable.just(heroEntityList);
        given(mockRestApi.heroEntityList()).willReturn(fakeObservable);

        // Create  data store
        cloudHeroDataStore = new CloudHeroDataStore(mockRestApi, mockHeroCache, mockHeroFilter);
    }

    @Test
    public void testGetHeroEntityListFromApiHappyCase() {
        cloudHeroDataStore.heroEntityList(null, null, null,
                null, null, null, null);
        verify(mockRestApi).heroEntityList();
    }

    @Test
    public void testGetHeroEntityDetailsFromApiHappyCase() {
        cloudHeroDataStore.heroEntityDetails(FAKE_HERO_ID);
        verify(mockRestApi).heroEntityList();
    }
}