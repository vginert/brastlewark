package com.vginert.brastlewark.domain.repository;

import com.vginert.brastlewark.domain.Hero;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Hero} related data.
 *
 * @author Vicente Giner Tendero
 */
public interface IHeroRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link Hero} filtered by query params.
     */
    Observable<List<Hero>> heroes(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                                  Integer minHeight, Integer maxHeight);

    /**
     * Get an {@link Observable} which will emit a {@link Hero}.
     *
     * @param heroId The hero id used to retrieve hero data.
     */
    Observable<Hero> hero(final int heroId);
}
