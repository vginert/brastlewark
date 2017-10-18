package com.vginert.brastlewark.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.vginert.brastlewark.data.entity.HeroEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 *
 * @author Vicente Giner Tendero
 */
public class HeroEntityJsonMapper {

    private final Gson gson;

    @Inject
    public HeroEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link HeroEntity}.
     *
     * @param heroJsonResponse A json representing a hero profile.
     * @return {@link HeroEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public HeroEntity transformHeroEntity(String heroJsonResponse) throws JsonSyntaxException {
        final Type heroEntityType = new TypeToken<HeroEntity>() {
        }.getType();
        return this.gson.fromJson(heroJsonResponse, heroEntityType);
    }

    /**
     * Transform from valid json string to List of {@link HeroEntity}.
     *
     * @param heroListJsonResponse A json representing a collection of heros.
     * @return List of {@link HeroEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<HeroEntity> transformHeroEntityCollection(String heroListJsonResponse)
            throws JsonSyntaxException {
        final Type listOfHeroEntityType = new TypeToken<List<HeroEntity>>() {
        }.getType();
        return this.gson.fromJson(heroListJsonResponse, listOfHeroEntityType);
    }
}
