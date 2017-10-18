package com.vginert.brastlewark.android.view;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a filter list.
 *
 * @author Vicente Giner Tendero
 */
public interface HeroFilterView extends BaseView {
    void restartNameFilter();
    void restartAgeFilter(int minAge, int maxAge);
    void showAllAges();
    void showEqualAge(int age);
    void showAgeRange(int from, int to);
    void restartWeightFilter(int minWeight, int maxWeight);
    void showAllWeights();
    void showEqualWeight(int weight);
    void showWeightRange(int from, int to);
    void restartHeightFilter(int minHeight, int maxHeight);
    void showAllHeights();
    void showEqualHeight(int height);
    void showHeightRange(int from, int to);
    void filter(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                Integer minHeight, Integer maxHeight);
}
