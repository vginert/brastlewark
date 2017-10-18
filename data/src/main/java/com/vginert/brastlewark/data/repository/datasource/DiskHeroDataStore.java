package com.vginert.brastlewark.data.repository.datasource;

import com.vginert.brastlewark.data.cache.IHeroCache;
import com.vginert.brastlewark.data.entity.HeroEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link IHeroDataStore} implementation based on file system data store.
 *
 * @author Vicente Giner Tendero
 */
class DiskHeroDataStore implements IHeroDataStore {

    private final IHeroCache heroCache;

    /**
     * Construct a {@link IHeroDataStore} based file system data store.
     *
     * @param heroCache A {@link IHeroCache} to cache data retrieved from the api.
     */
    DiskHeroDataStore(IHeroCache heroCache) {
        this.heroCache = heroCache;
    }

    @Override
    public Observable<List<HeroEntity>> heroEntityList(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                                                       Integer minHeight, Integer maxHeight) {
        //TODO: implement simple cache for storing/retrieving collections of heroes.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<HeroEntity> heroEntityDetails(final int heroId) {
        return this.heroCache.get(heroId);
    }
}
