package com.vginert.brastlewark.data.entity.mapper;

import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.domain.Hero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link HeroEntity} (in the data layer) to {@link Hero} in the
 * domain layer.
 *
 * @author Vicente Giner Tendero
 */
@Singleton
public class HeroEntityDataMapper {

    @Inject
    HeroEntityDataMapper() {
    }

    /**
     * Transform a {@link HeroEntity} into an {@link Hero}.
     *
     * @param heroEntity Object to be transformed.
     * @return {@link Hero} if valid {@link HeroEntity} otherwise null.
     */
    public Hero transform(HeroEntity heroEntity) {
        Hero hero = null;
        if (heroEntity != null) {
            hero = new Hero(heroEntity.getId());
            hero.setName(heroEntity.getName());
            hero.setThumbnail(heroEntity.getThumbnail());
            hero.setAge(heroEntity.getAge());
            hero.setWeight(heroEntity.getWeight());
            hero.setHeight(heroEntity.getHeight());
            hero.setHairColor(heroEntity.getHairColor());
            hero.setProfessions(heroEntity.getProfessions());
            hero.setFriends(heroEntity.getFriends());
        }
        return hero;
    }

    /**
     * Transform a List of {@link HeroEntity} into a Collection of {@link Hero}.
     *
     * @param heroEntityCollection Object Collection to be transformed.
     * @return {@link Hero} if valid {@link HeroEntity} otherwise null.
     */
    public List<Hero> transform(Collection<HeroEntity> heroEntityCollection) {
        final List<Hero> heroList = new ArrayList<>();
        for (HeroEntity heroEntity : heroEntityCollection) {
            final Hero hero = transform(heroEntity);
            if (hero != null) {
                heroList.add(hero);
            }
        }
        return heroList;
    }
}
