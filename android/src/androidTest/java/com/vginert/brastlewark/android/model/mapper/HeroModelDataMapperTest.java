package com.vginert.brastlewark.android.model.mapper;

import com.vginert.brastlewark.android.model.HeroModel;
import com.vginert.brastlewark.domain.Hero;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeroModelDataMapperTest extends TestCase {

    private static final int FAKE_HERO_ID = 8;
    private static final String FAKE_HERO_NAME = "Jon Snow";
    private static final String FAKE_HERO_THUMBNAIL = "https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553";
    private static final int FAKE_HERO_AGE = 22;
    private static final double FAKE_HERO_WEIGHT = 34.223;
    private static final double FAKE_HERO_HEIGHT = 24.564;
    private static final String FAKE_HERO_HAIR_COLOR = "Black";
    private static final List<String> FAKE_HERO_PROFESSIONS = Arrays.asList("Lord Commander", "Warden of the North", "King in the North");
    private static final List<String> FAKE_HERO_FRIENDS = Arrays.asList("Ygritte", "Daenerys Targaryen");

    private HeroModelDataMapper heroModelDataMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        heroModelDataMapper = new HeroModelDataMapper();
    }

    @Test
    public void testTransformHeroEntity() {
        Hero hero = createFakeHero();
        HeroModel heroModel = heroModelDataMapper.transform(hero);

        assertThat(heroModel, is(instanceOf(HeroModel.class)));
        assertThat(heroModel.getId(), is(FAKE_HERO_ID));
        assertThat(heroModel.getName(), is(FAKE_HERO_NAME));
        assertThat(heroModel.getThumbnail(), is(FAKE_HERO_THUMBNAIL));
        assertThat(heroModel.getAge(), is(FAKE_HERO_AGE));
        assertThat(heroModel.getWeight(), is(FAKE_HERO_WEIGHT));
        assertThat(heroModel.getHeight(), is(FAKE_HERO_HEIGHT));
        assertThat(heroModel.getHairColor(), is(FAKE_HERO_HAIR_COLOR));
        assertThat(heroModel.getProfessions(), is(FAKE_HERO_PROFESSIONS));
        assertThat(heroModel.getFriends(), is(FAKE_HERO_FRIENDS));
    }

    @Test
    public void testTransformHeroEntityCollection() {
        Hero mockHeroEntityOne = Mockito.mock(Hero.class);
        Hero mockHeroEntityTwo = Mockito.mock(Hero.class);

        List<Hero> heroEntityList = new ArrayList<>(5);
        heroEntityList.add(mockHeroEntityOne);
        heroEntityList.add(mockHeroEntityTwo);

        Collection<HeroModel> heroCollection = heroModelDataMapper.transform(heroEntityList);

        assertThat(heroCollection.toArray()[0], is(instanceOf(HeroModel.class)));
        assertThat(heroCollection.toArray()[1], is(instanceOf(HeroModel.class)));
        assertThat(heroCollection.size(), is(2));
    }

    private Hero createFakeHero() {
        Hero hero = new Hero(FAKE_HERO_ID);
        hero.setName(FAKE_HERO_NAME);
        hero.setThumbnail(FAKE_HERO_THUMBNAIL);
        hero.setAge(FAKE_HERO_AGE);
        hero.setWeight(FAKE_HERO_WEIGHT);
        hero.setHeight(FAKE_HERO_HEIGHT);
        hero.setHairColor(FAKE_HERO_HAIR_COLOR);
        hero.setProfessions(FAKE_HERO_PROFESSIONS);
        hero.setFriends(FAKE_HERO_FRIENDS);

        return hero;
    }
}