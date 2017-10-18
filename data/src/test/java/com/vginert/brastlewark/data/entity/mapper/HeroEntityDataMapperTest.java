package com.vginert.brastlewark.data.entity.mapper;

import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.domain.Hero;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class HeroEntityDataMapperTest {

    private static final int FAKE_HERO_ID = 8;
    private static final String FAKE_HERO_NAME = "Jon Snow";
    private static final String FAKE_HERO_THUMBNAIL = "https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553";
    private static final int FAKE_HERO_AGE = 22;
    private static final double FAKE_HERO_WEIGHT = 34.223;
    private static final double FAKE_HERO_HEIGHT = 24.564;
    private static final String FAKE_HERO_HAIR_COLOR = "Black";
    private static final List<String> FAKE_HERO_PROFESSIONS = Arrays.asList("Lord Commander", "Warden of the North", "King in the North");
    private static final List<String> FAKE_HERO_FRIENDS = Arrays.asList("Ygritte", "Daenerys Targaryen");

    private HeroEntityDataMapper heroEntityDataMapper;

    @Before
    public void setUp() throws Exception {
        heroEntityDataMapper = new HeroEntityDataMapper();
    }

    @Test
    public void testTransformHeroEntity() {
        HeroEntity heroEntity = createFakeHeroEntity();
        Hero hero = heroEntityDataMapper.transform(heroEntity);

        assertThat(hero, is(instanceOf(Hero.class)));
        assertThat(hero.getId(), is(FAKE_HERO_ID));
        assertThat(hero.getName(), is(FAKE_HERO_NAME));
        assertThat(hero.getThumbnail(), is(FAKE_HERO_THUMBNAIL));
        assertThat(hero.getAge(), is(FAKE_HERO_AGE));
        assertThat(hero.getWeight(), is(FAKE_HERO_WEIGHT));
        assertThat(hero.getHeight(), is(FAKE_HERO_HEIGHT));
        assertThat(hero.getHairColor(), is(FAKE_HERO_HAIR_COLOR));
        assertThat(hero.getProfessions(), is(FAKE_HERO_PROFESSIONS));
        assertThat(hero.getFriends(), is(FAKE_HERO_FRIENDS));
    }

    @Test
    public void testTransformHeroEntityCollection() {
        HeroEntity mockHeroEntityOne = mock(HeroEntity.class);
        HeroEntity mockHeroEntityTwo = mock(HeroEntity.class);

        List<HeroEntity> heroEntityList = new ArrayList<>(5);
        heroEntityList.add(mockHeroEntityOne);
        heroEntityList.add(mockHeroEntityTwo);

        Collection<Hero> heroCollection = heroEntityDataMapper.transform(heroEntityList);

        assertThat(heroCollection.toArray()[0], is(instanceOf(Hero.class)));
        assertThat(heroCollection.toArray()[1], is(instanceOf(Hero.class)));
        assertThat(heroCollection.size(), is(2));
    }

    private HeroEntity createFakeHeroEntity() {
        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setId(FAKE_HERO_ID);
        heroEntity.setName(FAKE_HERO_NAME);
        heroEntity.setThumbnail(FAKE_HERO_THUMBNAIL);
        heroEntity.setAge(FAKE_HERO_AGE);
        heroEntity.setWeight(FAKE_HERO_WEIGHT);
        heroEntity.setHeight(FAKE_HERO_HEIGHT);
        heroEntity.setHairColor(FAKE_HERO_HAIR_COLOR);
        heroEntity.setProfessions(FAKE_HERO_PROFESSIONS);
        heroEntity.setFriends(FAKE_HERO_FRIENDS);

        return heroEntity;
    }
}