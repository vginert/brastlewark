package com.vginert.brastlewark.data.net;

import com.vginert.brastlewark.data.entity.HeroEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * RestApi for retrieving data from the network.
 *
 * @author Vicente Giner Tendero
 */
public interface RestApi {

    String API_BASE_URL = "https://raw.githubusercontent.com/rrafols/mobile_test/master/";

    /**
     * Api url for getting all heroes
     */
    String API_URL_GET_HERO_LIST = API_BASE_URL + "data.json";

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link HeroEntity}.
     */
    Observable<List<HeroEntity>> heroEntityList();
}
