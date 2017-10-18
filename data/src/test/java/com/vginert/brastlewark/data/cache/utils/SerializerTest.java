package com.vginert.brastlewark.data.cache.utils;

import com.vginert.brastlewark.data.entity.HeroEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SerializerTest {

    private static final String JSON_RESPONSE = "{" +
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

    private Serializer serializer;

    @Before
    public void setUp() {
        serializer = new Serializer();
    }

    @Test
    public void testSerializeHappyCase() {
        final HeroEntity heroEntityOne = serializer.deserialize(JSON_RESPONSE, HeroEntity.class);
        final String jsonString = serializer.serialize(heroEntityOne, HeroEntity.class);
        final HeroEntity heroEntityTwo = serializer.deserialize(jsonString, HeroEntity.class);

        assertThat(heroEntityOne.getId(), is(heroEntityTwo.getId()));
        assertThat(heroEntityOne.getName(), is(equalTo(heroEntityTwo.getName())));
        assertThat(heroEntityOne.getThumbnail(), is(heroEntityTwo.getThumbnail()));
        assertThat(heroEntityOne.getAge(), is(heroEntityTwo.getAge()));
        assertThat(heroEntityOne.getWeight(), is(heroEntityTwo.getWeight()));
        assertThat(heroEntityOne.getHeight(), is(heroEntityTwo.getHeight()));
        assertThat(heroEntityOne.getHairColor(), is(heroEntityTwo.getHairColor()));
        assertThat(heroEntityOne.getProfessions(), is(heroEntityTwo.getProfessions()));
        assertThat(heroEntityOne.getFriends(), is(heroEntityTwo.getFriends()));
    }

    @Test
    public void testDeserializeHappyCase() {
        final HeroEntity heroEntity = serializer.deserialize(JSON_RESPONSE, HeroEntity.class);

        assertThat(heroEntity.getId(), is(0));
        assertThat(heroEntity.getName(), is("Jon Snow"));
        assertThat(heroEntity.getThumbnail(), is("https://vignette.wikia.nocookie.net/gameofthrones/images/a/a5/Profile-JonSnow-707.png/revision/latest?cb=20170828030553"));
        assertThat(heroEntity.getAge(), is(22));
        assertThat(heroEntity.getWeight(), is(34.223));
        assertThat(heroEntity.getHeight(), is(24.564));
        assertThat(heroEntity.getHairColor(), is("Black"));
        assertThat(heroEntity.getProfessions(), is(Arrays.asList("Lord Commander","Warden of the North","King in the North")));
        assertThat(heroEntity.getFriends(), is(Arrays.asList("Ygritte","Daenerys Targaryen")));
    }
}