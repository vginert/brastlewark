package com.vginert.brastlewark.data.repository.datasource;

import com.vginert.brastlewark.data.ApplicationTestCase;
import com.vginert.brastlewark.data.cache.HeroCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class HeroDataStoreFactoryTest extends ApplicationTestCase {

    private static final int FAKE_HERO_ID = 11;

    private HeroDataStoreFactory heroDataStoreFactory;

    @Mock
    private HeroCache mockHeroCache;

    @Before
    public void setUp() {
        heroDataStoreFactory = new HeroDataStoreFactory(RuntimeEnvironment.application, mockHeroCache);
    }

    @Test
    public void testCreateDiskDataStore() {
        given(mockHeroCache.isCached(FAKE_HERO_ID)).willReturn(true);
        given(mockHeroCache.isExpired()).willReturn(false);

        IHeroDataStore heroDataStore = heroDataStoreFactory.create(FAKE_HERO_ID);

        assertThat(heroDataStore, is(notNullValue()));
        assertThat(heroDataStore, is(instanceOf(DiskHeroDataStore.class)));

        verify(mockHeroCache).isCached(FAKE_HERO_ID);
    }

    @Test
    public void testCreateCloudDataStore() {
        given(mockHeroCache.isExpired()).willReturn(true);
        given(mockHeroCache.isCached(FAKE_HERO_ID)).willReturn(false);

        IHeroDataStore heroDataStore = heroDataStoreFactory.create(FAKE_HERO_ID);

        assertThat(heroDataStore, is(notNullValue()));
        assertThat(heroDataStore, is(instanceOf(CloudHeroDataStore.class)));
    }
}