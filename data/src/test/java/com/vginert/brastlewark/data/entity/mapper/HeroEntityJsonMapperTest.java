package com.vginert.brastlewark.data.entity.mapper;

import com.google.gson.JsonSyntaxException;
import com.vginert.brastlewark.data.entity.HeroEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HeroEntityJsonMapperTest {

    private static final String JSON_RESPONSE_USER_DETAILS = "{" +
                "\"id\":0," +
                "\"name\":\"Jon Snow\"," +
                "\"thumbnail\":\"https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553\"," +
                "\"age\":22," +
                "\"weight\":34.223," +
                "\"height\":24.564," +
                "\"hair_color\":\"Black\"," +
                "\"professions\":[\"Lord Commander\",\"Warden of the North\",\"King in the North\"]," +
                "\"friends\":[\"Ygritte\",\"Daenerys Targaryen\"]" +
            "}";

    private static final String JSON_RESPONSE_USER_COLLECTION = "[{" +
                "\"id\":0," +
                "\"name\":\"Jon Snow\"," +
                "\"thumbnail\":\"https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553\"," +
                "\"age\":22," +
                "\"weight\":34.223," +
                "\"height\":24.564," +
                "\"hair_color\":\"Black\"," +
                "\"professions\":[\"Lord Commander\",\"Warden of the North\",\"King in the North\"]," +
                "\"friends\":[\"Ygritte\",\"Daenerys Targaryen\"]" +
            "}, {" +
                "\"id\":1," +
                "\"name\":\"Tyrion Lannister\"," +
                "\"thumbnail\":\"https://vignette.wikia.nocookie.net/gameofthrones/images/5/58/Tyrion_main_s7_e6.jpg/revision/latest?cb=20170818050344\"," +
                "\"age\":39," +
                "\"weight\":20.253," +
                "\"height\":15.584," +
                "\"hair_color\":\"Blonde\"," +
                "\"professions\":[\"Lord of Casterly Rock\",\"Master of Coin\",\"Acting Hand of the King\",\"Hand of the Queen\"]," +
                "\"friends\":[\"Shae\"]" +
            "}]";

    private HeroEntityJsonMapper heroEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        heroEntityJsonMapper = new HeroEntityJsonMapper();
    }

    @Test
    public void testTransformHeroEntityHappyCase() {
        HeroEntity heroEntity = heroEntityJsonMapper.transformHeroEntity(JSON_RESPONSE_USER_DETAILS);

        Assert.assertThat(heroEntity.getId(), is(0));
        Assert.assertThat(heroEntity.getName(), is("Jon Snow"));
        Assert.assertThat(heroEntity.getThumbnail(), is("https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553"));
        Assert.assertThat(heroEntity.getAge(), is(22));
        Assert.assertThat(heroEntity.getWeight(), is(34.223));
        Assert.assertThat(heroEntity.getHeight(), is(24.564));
        Assert.assertThat(heroEntity.getHairColor(), is("Black"));
        Assert.assertThat(heroEntity.getProfessions(), is(Arrays.asList("Lord Commander","Warden of the North","King in the North")));
        Assert.assertThat(heroEntity.getFriends(), is(Arrays.asList("Ygritte","Daenerys Targaryen")));
    }

    @Test
    public void testTransformHeroEntityCollectionHappyCase() {
        Collection<HeroEntity> heroEntityCollection =
                heroEntityJsonMapper.transformHeroEntityCollection(
                        JSON_RESPONSE_USER_COLLECTION);

        assertThat(((HeroEntity) heroEntityCollection.toArray()[0]).getId(), is(0));
        assertThat(((HeroEntity) heroEntityCollection.toArray()[1]).getId(), is(1));
        assertThat(heroEntityCollection.size(), is(2));
    }

    @Test
    public void testTransformHeroEntityNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        heroEntityJsonMapper.transformHeroEntity("invalid response");
    }

    @Test
    public void testTransformHeroEntityCollectionNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        heroEntityJsonMapper.transformHeroEntityCollection("invalid response");
    }
}