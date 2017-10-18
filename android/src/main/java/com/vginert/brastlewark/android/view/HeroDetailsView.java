package com.vginert.brastlewark.android.view;

import com.vginert.brastlewark.android.model.HeroModel;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a hero profile.
 *
 * @author Vicente Giner Tendero
 */
public interface HeroDetailsView extends LoadDataView {

    /**
     * Render a hero name in the UI.
     *
     * @param name The {@link HeroModel} name that will be shown.
     */
    void renderHeroName(String name);

    /**
     * Render a hero thumbnail in the UI.
     *
     * @param thumbnailUrl The {@link HeroModel} url thumbnail that will be shown.
     */
    void renderHeroThumbnail(String thumbnailUrl);

    /**
     * Render a hero age in the UI.
     *
     * @param age The {@link HeroModel} age that will be shown.
     */
    void renderHeroAge(int age);

    /**
     * Render a hero weight in the UI.
     *
     * @param weight The {@link HeroModel} weight that will be shown.
     */
    void renderHeroWeight(double weight);

    /**
     * Render a hero height in the UI.
     *
     * @param height The {@link HeroModel} height that will be shown.
     */
    void renderHeroHeight(double height);

    /**
     * Render a hero hair color in the UI.
     *
     * @param hairColor The {@link HeroModel} hair color that will be shown.
     */
    void renderHeroHairColor(String hairColor);

    /**
     * Render a hero professions in the UI.
     *
     * @param professions The professions list that will be shown.
     */
    void renderHeroProfessions(List<String> professions);

    /**
     * Render a hero friends in the UI.
     *
     * @param friends The friends list that will be shown.
     */
    void renderHeroFriends(List<String> friends);

    /**
     * Show the hero professions view in the UI.
     */
    void showProfessions();

    /**
     * Hide the hero professions view in the UI.
     */
    void hideProfessions();

    /**
     * Show the hero friends view in the UI.
     */
    void showFriends();

    /**
     * Hide the hero friends view in the UI.
     */
    void hideFriends();
}
