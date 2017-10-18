package com.vginert.brastlewark.android.view;

import com.vginert.brastlewark.android.model.HeroModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link HeroModel}.
 *
 * @author Vicente Giner Tendero
 */
public interface HeroListView extends LoadDataView {
    /**
     * Render a hero list in the UI.
     *
     * @param heroModelCollection The collection of {@link HeroModel} that will be shown.
     */
    void renderHeroList(Collection<HeroModel> heroModelCollection);

    /**
     * View a {@link HeroModel} profile/details.
     *
     * @param heroModel The hero that will be shown.
     */
    void viewHero(HeroModel heroModel);

    /**
     * Show a the filter toolbar in the UI.
     */
    void showFilterToolbar();

    /**
     * Hide a the filter toolbar in the UI.
     */
    void hideFilterToolbar();

    /**
     * Show a the filter delete button in the UI.
     */
    void showDeleteFilterButton();

    /**
     * Hide a the filter delete button in the UI.
     */
    void hideDeleteFilterButton();

    /**
     * Render a name filter chip in the UI.
     *
     * @param name The name filter.
     */
    void showNameChip(String name);

    /**
     * Hide a the name filter chip the UI.
     */
    void hideNameChip();

    /**
     * Render a age from-to filter chip in the UI.
     *
     * @param from The age from filter.
     * @param to The age to filter.
     */
    void showAgeChip(int from, int to);

    /**
     * Render a age from filter chip in the UI.
     *
     * @param from The age from filter.
     */
    void showFromAgeChip(int from);

    /**
     * Render a age to filter chip in the UI.
     *
     * @param to The age to filter.
     */
    void showToAgeChip(int to);

    /**
     * Hide a the age filter chip the UI.
     */
    void hideAgeChip();

    /**
     * Render a weight from-to filter chip in the UI.
     *
     * @param from The weight from filter.
     * @param to The weight to filter.
     */
    void showWeightChip(int from, int to);

    /**
     * Render a weight from filter chip in the UI.
     *
     * @param from The weight from filter.
     */
    void showFromWeightChip(int from);

    /**
     * Render a weight to filter chip in the UI.
     *
     * @param to The weight to filter.
     */
    void showToWeightChip(int to);

    /**
     * Hide a the weight filter chip the UI.
     */
    void hideWeightChip();

    /**
     * Render a height from-to filter chip in the UI.
     *
     * @param from The height from filter.
     * @param to The height to filter.
     */
    void showHeightChip(int from, int to);

    /**
     * Render a height from filter chip in the UI.
     *
     * @param from The height from filter.
     */
    void showFromHeightChip(int from);

    /**
     * Render a height to filter chip in the UI.
     *
     * @param to The height to filter.
     */
    void showToHeightChip(int to);

    /**
     * Hide a the height filter chip the UI.
     */
    void hideHeightChip();

    /**
     * Show a no result image the UI.
     */
    void showNoResultsImage();

    /**
     * Show a error result image the UI.
     *
     * @param drawableId The drawable id to render the image.
     */
    void showErrorImage(int drawableId);

    /**
     * Hide the error image the UI.
     */
    void hideErrorImage();
}
