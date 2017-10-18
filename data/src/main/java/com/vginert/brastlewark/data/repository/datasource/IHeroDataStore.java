package com.vginert.brastlewark.data.repository.datasource;

import com.vginert.brastlewark.data.entity.HeroEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 *
 * @author Vicente Giner Tendero
 */
public interface IHeroDataStore {

    /**
     * Get an {@link Observable} which will emit a List of {@link HeroEntity}.
     */
    Observable<List<HeroEntity>> heroEntityList(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                                                Integer minHeight, Integer maxHeight);

    /**
     * Get an {@link Observable} which will emit a {@link HeroEntity} by its id.
     *
     * @param heroId The id to retrieve hero data.
     */
    Observable<HeroEntity> heroEntityDetails(final int heroId);
}
