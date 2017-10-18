package com.vginert.brastlewark.data.filter;

import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.exception.HeroNotFoundException;

import org.assertj.core.data.Index;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HeroFilterTest {

    private static final String FAKE_NAME_FILTER = "Jon";
    private static final Integer FAKE_MIN_AGE_FILTER = 10;
    private static final Integer FAKE_MAX_AGE_FILTER = 30;
    private static final Integer FAKE_MIN_WEIGHT_FILTER = 30;
    private static final Integer FAKE_MAX_WEIGHT_FILTER = 40;
    private static final Integer FAKE_MIN_HEIGHT_FILTER = 20;
    private static final Integer FAKE_MAX_HEIGHT_FILTER = 30;

    private static final int FAKE_HERO_NOT_FOUND_ID = 100;

    private static final int FAKE_HERO_1_ID = 0;
    private static final String FAKE_HERO_1_NAME = "Jon Snow";
    private static final String FAKE_HERO_1_THUMBNAIL = "https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553";
    private static final int FAKE_HERO_1_AGE = 22;
    private static final double FAKE_HERO_1_WEIGHT = 34.223;
    private static final double FAKE_HERO_1_HEIGHT = 24.564;
    private static final String FAKE_HERO_1_HAIR_COLOR = "Black";
    private static final List<String> FAKE_HERO_1_PROFESSIONS = Arrays.asList("Lord Commander", "Warden of the North", "King in the North");
    private static final List<String> FAKE_HERO_1_FRIENDS = Arrays.asList("Ygritte", "Daenerys Targaryen");

    private static final int FAKE_HERO_2_ID = 1;
    private static final String FAKE_HERO_2_NAME = "Tyrion Lannister";
    private static final String FAKE_HERO_2_THUMBNAIL = "https://vignette.wikia.nocookie.net/gameofthrones/images/5/58/Tyrion_main_s7_e6.jpg/revision/latest?cb=20170818050344";
    private static final int FAKE_HERO_2_AGE = 39;
    private static final double FAKE_HERO_2_WEIGHT = 20.253;
    private static final double FAKE_HERO_2_HEIGHT = 15.584;
    private static final String FAKE_HERO_2_HAIR_COLOR = "Blonde";
    private static final List<String> FAKE_HERO_2_PROFESSIONS = Arrays.asList("Lord of Casterly Rock", "Master of Coin", "Acting Hand of the King", "Hand of the Queen");
    private static final List<String> FAKE_HERO_2_FRIENDS = Arrays.asList("Shae");

    private HeroFilter heroFilter;
    private List<HeroEntity> heroList;
    private HeroEntity hero1;
    private HeroEntity hero2;

    @Before
    public void setUp() {
        this.hero1 = new HeroEntity(FAKE_HERO_1_ID, FAKE_HERO_1_NAME, FAKE_HERO_1_THUMBNAIL, FAKE_HERO_1_AGE,
                FAKE_HERO_1_WEIGHT, FAKE_HERO_1_HEIGHT, FAKE_HERO_1_HAIR_COLOR, FAKE_HERO_1_PROFESSIONS, FAKE_HERO_1_FRIENDS);
        this.hero2 = new HeroEntity(FAKE_HERO_2_ID, FAKE_HERO_2_NAME, FAKE_HERO_2_THUMBNAIL, FAKE_HERO_2_AGE,
                FAKE_HERO_2_WEIGHT, FAKE_HERO_2_HEIGHT, FAKE_HERO_2_HAIR_COLOR, FAKE_HERO_2_PROFESSIONS, FAKE_HERO_2_FRIENDS);

        this.heroList = new ArrayList<>();
        this.heroList.add(this.hero1);
        this.heroList.add(this.hero2);
        this.heroFilter = new HeroFilter();
    }

    @Test
    public void filterHappyCase() {
        List<HeroEntity> result = this.heroFilter.filter(this.heroList, FAKE_NAME_FILTER, FAKE_MIN_AGE_FILTER,
                FAKE_MAX_AGE_FILTER, FAKE_MIN_WEIGHT_FILTER, FAKE_MAX_WEIGHT_FILTER, FAKE_MIN_HEIGHT_FILTER, FAKE_MAX_HEIGHT_FILTER);
        assertThat(result).isNotNull();
        assertThat(result).contains(this.hero1, Index.atIndex(0));
    }

    @Test
    public void filterNull() {
        List<HeroEntity> result = this.heroFilter.filter(null, null, null, null, null, null, null, null);
        assertThat(result).isNull();
    }

    @Test
    public void filterNullParams() {
        List<HeroEntity> result = this.heroFilter.filter(this.heroList, null, null, null, null, null, null, null);
        assertThat(result).isNotNull();
        assertThat(result.containsAll(this.heroList)).isTrue();
    }

    @Test
    public void filterEmptyList() {
        this.heroList = new ArrayList<>();
        List<HeroEntity> result = this.heroFilter.filter(this.heroList, null, null, null, null, null, null, null);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void filterByIdHappyCAse() throws HeroNotFoundException {
        HeroEntity result = this.heroFilter.filter(this.heroList, this.hero2.getId());
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.hero2);
    }

    @Test(expected = HeroNotFoundException.class)
    public void filterByIdNotFound() throws HeroNotFoundException {
        this.heroFilter.filter(this.heroList, FAKE_HERO_NOT_FOUND_ID);
    }

    @Test(expected = HeroNotFoundException.class)
    public void filterByIdEmptyList() throws HeroNotFoundException {
        this.heroList = new ArrayList<>();
        this.heroFilter.filter(this.heroList, this.hero1.getId());
    }

    @Test(expected = HeroNotFoundException.class)
    public void filterByIdNull() throws HeroNotFoundException {
        this.heroFilter.filter(null, this.hero1.getId());
    }

    @Test
    public void sortHappyCase() {
        List<HeroEntity> result = this.heroFilter.sort(this.heroList);
        assertThat(result).isNotNull();
        assertThat(result).isSortedAccordingTo((Comparator<HeroEntity>) (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    @Test
    public void sortNull() {
        List<HeroEntity> result = this.heroFilter.sort(null);
        assertThat(result).isNull();
    }

}