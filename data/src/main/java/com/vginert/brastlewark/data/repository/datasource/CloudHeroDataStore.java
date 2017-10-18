package com.vginert.brastlewark.data.repository.datasource;

import com.vginert.brastlewark.data.cache.IHeroCache;
import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.filter.HeroFilter;
import com.vginert.brastlewark.data.net.RestApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link IHeroDataStore} implementation based on connections to the api (Cloud).
 *
 * @author Vicente Giner Tendero
 */
class CloudHeroDataStore implements IHeroDataStore {

    private final RestApi restApi;
    private final IHeroCache heroCache;
    private final HeroFilter heroFilter;

    /**
     * Construct a {@link IHeroDataStore} based on connections to the api (Cloud).
     *
     * @param restApi   The {@link RestApi} implementation to use.
     * @param heroCache A {@link IHeroCache} to cache data retrieved from the api.
     */
    CloudHeroDataStore(RestApi restApi, IHeroCache heroCache, HeroFilter heroFilter) {
        this.restApi = restApi;
        this.heroCache = heroCache;
        this.heroFilter = heroFilter;
    }

    @Override
    public Observable<List<HeroEntity>> heroEntityList(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                                                       Integer minHeight, Integer maxHeight) {
        return this.restApi.heroEntityList().map(heroList -> {
            heroList = heroFilter.sort(heroList);
            return heroFilter.filter(heroList, name, minAge, maxAge,
                    minWeight, maxWeight, minHeight, maxHeight);
        }).doOnNext(this.heroCache::put);
    }

    @Override
    public Observable<HeroEntity> heroEntityDetails(final int heroId) {
        return this.restApi.heroEntityList().map(heroList -> heroFilter.filter(heroList, heroId))
                .doOnNext(CloudHeroDataStore.this.heroCache::put);
    }
}
