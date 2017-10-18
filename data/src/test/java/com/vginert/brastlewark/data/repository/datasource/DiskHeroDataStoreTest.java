package com.vginert.brastlewark.data.repository.datasource;

import com.vginert.brastlewark.data.cache.IHeroCache;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DiskHeroDataStoreTest {

    private static final int FAKE_HERO_ID = 11;
    private static final String FAKE_NAME_FILTER = "Jon Snow";
    private static final Integer FAKE_MIN_AGE_FILTER = 1;
    private static final Integer FAKE_MAX_AGE_FILTER = 5;
    private static final Integer FAKE_MIN_WEIGHT_FILTER = 10;
    private static final Integer FAKE_MAX_WEIGHT_FILTER = 15;
    private static final Integer FAKE_MIN_HEIGHT_FILTER = 20;
    private static final Integer FAKE_MAX_HEIGHT_FILTER = 25;

    private DiskHeroDataStore diskHeroDataStore;

    @Mock
    private IHeroCache mockHeroCache;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        diskHeroDataStore = new DiskHeroDataStore(mockHeroCache);
    }

    @Test
    public void testGetHeroEntityListUnsupported() {
        expectedException.expect(UnsupportedOperationException.class);
        diskHeroDataStore.heroEntityList(FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER, FAKE_MAX_AGE_FILTER,
                FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER);
    }

    @Test
    public void testGetHeroEntityDetailsFromCache() {
        diskHeroDataStore.heroEntityDetails(FAKE_HERO_ID);
        verify(mockHeroCache).get(FAKE_HERO_ID);
    }
}