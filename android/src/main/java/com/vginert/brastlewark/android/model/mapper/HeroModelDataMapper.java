package com.vginert.brastlewark.android.model.mapper;

import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.android.model.HeroModel;
import com.vginert.brastlewark.domain.Hero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Hero} (in the domain layer) to {@link HeroModel} in the
 * presentation layer.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
public class HeroModelDataMapper {

  @Inject
  public HeroModelDataMapper() {}

  /**
   * Transform a {@link Hero} into an {@link HeroModel}.
   *
   * @param hero Object to be transformed.
   * @return {@link HeroModel}.
   */
  public HeroModel transform(Hero hero) {
    if (hero == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final HeroModel heroModel = new HeroModel(hero.getId());
      heroModel.setName(hero.getName());
      heroModel.setThumbnail(hero.getThumbnail());
      heroModel.setAge(hero.getAge());
      heroModel.setWeight(hero.getWeight());
      heroModel.setHeight(hero.getHeight());
      heroModel.setHairColor(hero.getHairColor());
      heroModel.setProfessions(hero.getProfessions());
      heroModel.setFriends(hero.getFriends());

    return heroModel;
  }

  /**
   * Transform a Collection of {@link Hero} into a Collection of {@link HeroModel}.
   *
   * @param herosCollection Objects to be transformed.
   * @return List of {@link HeroModel}.
   */
  public Collection<HeroModel> transform(Collection<Hero> herosCollection) {
    Collection<HeroModel> heroModelsCollection;

    if (herosCollection != null && !herosCollection.isEmpty()) {
      heroModelsCollection = new ArrayList<>();
      for (Hero hero : herosCollection) {
        heroModelsCollection.add(transform(hero));
      }
    } else {
      heroModelsCollection = Collections.emptyList();
    }

    return heroModelsCollection;
  }
}
