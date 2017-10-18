package com.vginert.brastlewark.data.filter;

import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.exception.HeroNotFoundException;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used for filter Heroes
 *
 * @author Vicente Giner Tendero
 */

public class HeroFilter {

    @Inject
    public HeroFilter() {
    }

    public List<HeroEntity> filter(List<HeroEntity> heroList, final String name, final Integer minAge, final Integer maxAge,
                       final Integer minWeight, final Integer maxWeight, final Integer minHeight,
                       final Integer maxHeight) {
        if(heroList != null && !heroList.isEmpty()) {
            if (name != null) {
                heroList.removeIf(hero -> !hero.getName().toLowerCase().contains(name.toLowerCase()));
            }
            if (minAge != null) {
                heroList.removeIf(hero -> hero.getAge() < minAge);
            }
            if (maxAge != null) {
                heroList.removeIf(hero -> hero.getAge() > maxAge);
            }
            if (minWeight != null) {
                heroList.removeIf(hero -> hero.getWeight() < minWeight);
            }
            if (maxWeight != null) {
                heroList.removeIf(hero -> hero.getWeight() > maxWeight);
            }
            if (minHeight != null) {
                heroList.removeIf(hero -> hero.getHeight() < minHeight);
            }
            if (maxHeight != null) {
                heroList.removeIf(hero -> hero.getHeight() > maxHeight);
            }
        }
        return heroList;
    }

    public HeroEntity filter(List<HeroEntity> heroList, int id) throws HeroNotFoundException {
        if(heroList != null) {
            for (HeroEntity heroEntity : heroList) {
                if (heroEntity.getId() == id)
                    return heroEntity;
            }
        }
        throw new HeroNotFoundException();
    }

    public List<HeroEntity> sort(List<HeroEntity> heroList) {
        if(heroList != null && !heroList.isEmpty()) {
            Collections.sort(heroList, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        }
        return heroList;
    }
}
