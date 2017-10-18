package com.vginert.brastlewark.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

    import static org.assertj.core.api.Assertions.assertThat;

public class HeroTest {

    private static final int FAKE_HERO_ID = 0;
    private static final String FAKE_HERO_NAME = "Jon Snow";
    private static final String FAKE_HERO_THUMBNAIL = "https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553";
    private static final int FAKE_HERO_AGE = 22;
    private static final double FAKE_HERO_WEIGHT = 34.223;
    private static final double FAKE_HERO_HEIGHT = 24.564;
    private static final String FAKE_HERO_HAIR_COLOR = "Black";
    private static final List<String> FAKE_HERO_PROFESSIONS = Arrays.asList("Lord Commander", "Warden of the North", "King in the North");
    private static final List<String> FAKE_HERO_FRIENDS = Arrays.asList("Ygritte", "Daenerys Targaryen");

    private static final int NEW_FAKE_HERO_ID = 1;
    private static final String NEW_FAKE_HERO_NAME = "Tyrion Lannister";
    private static final String NEW_FAKE_HERO_THUMBNAIL = "https://vignette.wikia.nocookie.net/gameofthrones/images/5/58/Tyrion_main_s7_e6.jpg/revision/latest?cb=20170818050344";
    private static final int NEW_FAKE_HERO_AGE = 39;
    private static final double NEW_FAKE_HERO_WEIGHT = 20.253;
    private static final double NEW_FAKE_HERO_HEIGHT = 15.584;
    private static final String NEW_FAKE_HERO_HAIR_COLOR = "Blonde";
    private static final List<String> NEW_FAKE_HERO_PROFESSIONS = Arrays.asList("Lord of Casterly Rock", "Master of Coin", "Acting Hand of the King", "Hand of the Queen");
    private static final List<String> NEW_FAKE_HERO_FRIENDS = Arrays.asList("Shae");

    private Hero hero;

    @Before
    public void setUp() throws Exception {
        this.hero = new Hero(FAKE_HERO_ID, FAKE_HERO_NAME, FAKE_HERO_THUMBNAIL, FAKE_HERO_AGE,
                FAKE_HERO_WEIGHT, FAKE_HERO_HEIGHT, FAKE_HERO_HAIR_COLOR, FAKE_HERO_PROFESSIONS, FAKE_HERO_FRIENDS);
    }

    @Test
    public void testConstructor() {
        this.hero = new Hero(FAKE_HERO_ID);
        final int heroId = this.hero.getId();
        assertThat(heroId).isEqualTo(FAKE_HERO_ID);
    }

    @Test
    public void testFullConstructor() {
        final int heroId = this.hero.getId();
        final String heroName = this.hero.getName();
        final String heroThumbnail = this.hero.getThumbnail();
        final int heroAge = this.hero.getAge();
        final double heroWeight = this.hero.getWeight();
        final double heroHeight = this.hero.getHeight();
        final String heroHairColor = this.hero.getHairColor();
        final List<String> heroProfessions = this.hero.getProfessions();
        final List<String> heroFriends = this.hero.getFriends();

        assertThat(heroId).isEqualTo(FAKE_HERO_ID);
        assertThat(heroName).isEqualTo(FAKE_HERO_NAME);
        assertThat(heroThumbnail).isEqualTo(FAKE_HERO_THUMBNAIL);
        assertThat(heroAge).isEqualTo(FAKE_HERO_AGE);
        assertThat(heroWeight).isEqualTo(FAKE_HERO_WEIGHT);
        assertThat(heroHeight).isEqualTo(FAKE_HERO_HEIGHT);
        assertThat(heroHairColor).isEqualTo(FAKE_HERO_HAIR_COLOR);
        assertThat(heroProfessions).isEqualTo(FAKE_HERO_PROFESSIONS);
        assertThat(heroFriends).isEqualTo(FAKE_HERO_FRIENDS);
    }

    @Test
    public void setId() throws Exception {
        this.hero.setId(NEW_FAKE_HERO_ID);
        final int heroId = this.hero.getId();
        assertThat(heroId).isEqualTo(NEW_FAKE_HERO_ID);
    }

    @Test
    public void setName() throws Exception {
        this.hero.setName(NEW_FAKE_HERO_NAME);
        final String heroName = this.hero.getName();
        assertThat(heroName).isEqualTo(NEW_FAKE_HERO_NAME);
    }

    @Test
    public void setThumbnail() throws Exception {
        this.hero.setThumbnail(NEW_FAKE_HERO_THUMBNAIL);
        final String heroThumbnail = this.hero.getThumbnail();
        assertThat(heroThumbnail).isEqualTo(NEW_FAKE_HERO_THUMBNAIL);
    }

    @Test
    public void setAge() throws Exception {
        this.hero.setAge(NEW_FAKE_HERO_AGE);
        final int heroAge = this.hero.getAge();
        assertThat(heroAge).isEqualTo(NEW_FAKE_HERO_AGE);
    }

    @Test
    public void setWeight() throws Exception {
        this.hero.setWeight(NEW_FAKE_HERO_WEIGHT);
        final double heroWeight = this.hero.getWeight();
        assertThat(heroWeight).isEqualTo(NEW_FAKE_HERO_WEIGHT);
    }

    @Test
    public void setHeight() throws Exception {
        this.hero.setHeight(NEW_FAKE_HERO_HEIGHT);
        final double heroHeight = this.hero.getHeight();
        assertThat(heroHeight).isEqualTo(NEW_FAKE_HERO_HEIGHT);
    }

    @Test
    public void setHairColor() throws Exception {
        this.hero.setHairColor(NEW_FAKE_HERO_HAIR_COLOR);
        final String heroHairColor = this.hero.getHairColor();
        assertThat(heroHairColor).isEqualTo(NEW_FAKE_HERO_HAIR_COLOR);
    }

    @Test
    public void setProfessions() throws Exception {
        this.hero.setProfessions(NEW_FAKE_HERO_PROFESSIONS);
        final List<String> heroProfessions = this.hero.getProfessions();
        assertThat(heroProfessions).isEqualTo(NEW_FAKE_HERO_PROFESSIONS);
    }

    @Test
    public void setFriends() throws Exception {
        this.hero.setFriends(NEW_FAKE_HERO_FRIENDS);
        final List<String> heroFriends = this.hero.getFriends();
        assertThat(heroFriends).isEqualTo(NEW_FAKE_HERO_FRIENDS);
    }
}