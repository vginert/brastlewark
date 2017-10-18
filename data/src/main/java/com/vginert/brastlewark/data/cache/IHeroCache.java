package com.vginert.brastlewark.data.cache;

import com.vginert.brastlewark.data.entity.HeroEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * An interface representing a hero Cache.
 *
 * @author Vicente Giner Tendero
 */
public interface IHeroCache {
    /**
     * Gets an {@link Observable} which will emit a {@link HeroEntity}.
     *
     * @param heroId The hero id to retrieve data.
     */
    Observable<HeroEntity> get(final int heroId);

    /**
     * Puts an element into the cache.
     *
     * @param heroEntity Element to insert in the cache.
     */
    void put(HeroEntity heroEntity);

    /**
     * Puts an element list into the cache.
     *
     * @param heroEntityList List of elements to insert in the cache.
     */
    void put(List<HeroEntity> heroEntityList);

    /**
     * Checks if an element (Hero) exists in the cache.
     *
     * @param heroId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final int heroId);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}
