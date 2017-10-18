package com.vginert.brastlewark.android.presenter;

import android.support.annotation.NonNull;

import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.android.view.HeroFilterView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
public class HeroFilterPresenter implements Presenter {

    private static final Integer MIN_AGE_FILTER_VALUE = 0;
    private static final Integer MAX_AGE_FILTER_VALUE = 350;
    private static final Integer MIN_WEIGHT_FILTER_VALUE = 0;
    private static final Integer MAX_WEIGHT_FILTER_VALUE = 100;
    private static final Integer MIN_HEIGHT_FILTER_VALUE = 0;
    private static final Integer MAX_HEIGHT_FILTER_VALUE = 200;

    private HeroFilterView heroFilterView;

    @Inject
    public HeroFilterPresenter() {

    }

    public void setView(@NonNull HeroFilterView view) {
        this.heroFilterView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.heroFilterView = null;
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving filter values.
     */
    public void initialize() {
        this.setFilterValues();
    }

    private void setFilterValues() {
        this.restartFilters();
    }

    public void ageRangeChange(int left, int right) {
        if(left == MIN_AGE_FILTER_VALUE && right == MAX_AGE_FILTER_VALUE) {
            this.heroFilterView.showAllAges();
        } else if (right == MAX_AGE_FILTER_VALUE && left == MAX_AGE_FILTER_VALUE) {

        } else if(left == right) {
            this.heroFilterView.showEqualAge(left);
        } else {
            this.heroFilterView.showAgeRange(left, right);
        }
    }

    public void weightRangeChange(int left, int right) {
        if(left == MIN_WEIGHT_FILTER_VALUE && right == MAX_WEIGHT_FILTER_VALUE) {
            this.heroFilterView.showAllWeights();
        } else if (right == MAX_WEIGHT_FILTER_VALUE && left == MAX_WEIGHT_FILTER_VALUE) {

        } else if(left == right) {
            this.heroFilterView.showEqualWeight(left);
        } else {
            this.heroFilterView.showWeightRange(left, right);
        }
    }

    public void heightRangeChange(int left, int right) {
        if(left == MIN_HEIGHT_FILTER_VALUE && right == MAX_HEIGHT_FILTER_VALUE) {
            this.heroFilterView.showAllHeights();
        } else if (right == MAX_HEIGHT_FILTER_VALUE && left == MAX_HEIGHT_FILTER_VALUE) {

        } else if(left == right) {
            this.heroFilterView.showEqualHeight(left);
        } else {
            this.heroFilterView.showHeightRange(left, right);
        }
    }

    public void restartFilters() {
        this.heroFilterView.restartNameFilter();
        this.heroFilterView.restartAgeFilter(MIN_AGE_FILTER_VALUE, MAX_AGE_FILTER_VALUE);
        this.heroFilterView.restartWeightFilter(MIN_WEIGHT_FILTER_VALUE, MAX_WEIGHT_FILTER_VALUE);
        this.heroFilterView.restartHeightFilter(MIN_HEIGHT_FILTER_VALUE, MAX_HEIGHT_FILTER_VALUE);
    }

    public void filter(String name, int minAge, int maxAge, int minWeight, int maxWeight,
                       int minHeight, int maxHeight) {
        String nameFilter = null;
        if(name != null && !name.isEmpty()) {
            nameFilter = name;
        }
        Integer minAgeFilter = null;
        if(minAge != MIN_AGE_FILTER_VALUE) {
            minAgeFilter = minAge;
        }
        Integer maxAgeFilter = null;
        if(maxAge != MAX_AGE_FILTER_VALUE) {
            maxAgeFilter = maxAge;
        }
        Integer minWeightFilter = null;
        if(minWeight != MIN_WEIGHT_FILTER_VALUE) {
            minWeightFilter = minWeight;
        }
        Integer maxWeightFilter = null;
        if(maxWeight != MAX_WEIGHT_FILTER_VALUE) {
            maxWeightFilter = maxWeight;
        }
        Integer minHeightFilter = null;
        if(minHeight != MIN_HEIGHT_FILTER_VALUE) {
            minHeightFilter = minHeight;
        }
        Integer maxHeightFilter = null;
        if(maxHeight != MAX_HEIGHT_FILTER_VALUE) {
            maxHeightFilter = maxHeight;
        }

        this.heroFilterView.filter(nameFilter, minAgeFilter, maxAgeFilter, minWeightFilter, maxWeightFilter,
                minHeightFilter, maxHeightFilter);
    }
}
