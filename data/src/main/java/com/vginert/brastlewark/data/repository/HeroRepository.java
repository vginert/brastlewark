package com.vginert.brastlewark.data.repository;

import com.vginert.brastlewark.data.entity.mapper.HeroEntityDataMapper;
import com.vginert.brastlewark.data.repository.datasource.HeroDataStoreFactory;
import com.vginert.brastlewark.data.repository.datasource.IHeroDataStore;
import com.vginert.brastlewark.domain.Hero;
import com.vginert.brastlewark.domain.repository.IHeroRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link IHeroRepository} for retrieving hero data.
 *
 * @author Vicente Giner Tendero
 */
@Singleton
public class HeroRepository implements IHeroRepository {

    private final HeroDataStoreFactory heroDataStoreFactory;
    private final HeroEntityDataMapper heroEntityDataMapper;

    /**
     * Constructs a {@link HeroRepository}.
     *
     * @param dataStoreFactory     A factory to construct different data source implementations.
     * @param heroEntityDataMapper {@link HeroEntityDataMapper}.
     */
    @Inject
    HeroRepository(HeroDataStoreFactory dataStoreFactory,
                   HeroEntityDataMapper heroEntityDataMapper) {
        this.heroDataStoreFactory = dataStoreFactory;
        this.heroEntityDataMapper = heroEntityDataMapper;
    }

    @Override
    public Observable<List<Hero>> heroes(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                                         Integer minHeight, Integer maxHeight) {
        //we always get all heroes from the cloud
        final IHeroDataStore heroDataStore = this.heroDataStoreFactory.createCloudDataStore();
        return heroDataStore.heroEntityList(name, minAge, maxAge,
                minWeight, maxWeight, minHeight, maxHeight).map(this.heroEntityDataMapper::transform);
    }

    @Override
    public Observable<Hero> hero(int heroId) {
        final IHeroDataStore heroDataStore = this.heroDataStoreFactory.create(heroId);
        return heroDataStore.heroEntityDetails(heroId).map(this.heroEntityDataMapper::transform);
    }
}
