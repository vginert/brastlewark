package com.vginert.brastlewark.android.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.components.HeroComponent;
import com.vginert.brastlewark.android.presenter.HeroFilterPresenter;
import com.vginert.brastlewark.android.view.HeroFilterView;
import com.vginert.brastlewark.android.view.custom.ClearEditText;
import com.vginert.rangebar.RangeBar;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment dialog that shows a filter for the list of Heroes.
 *
 * @author Vicente Giner Tendero
 */
public class HeroFilterFragment extends BaseFragment implements HeroFilterView,
        RangeBar.OnRangeBarChangeListener {

    @Inject
    HeroFilterPresenter heroFilterPresenter;

    @BindView(R.id.et_name)
    ClearEditText et_name;
    @BindView(R.id.rb_age)
    RangeBar rb_age;
    @BindView(R.id.rb_weight)
    RangeBar rb_weight;
    @BindView(R.id.rb_height)
    RangeBar rb_height;
    @BindView(R.id.tv_age_range_text)
    TextView tv_age_range_text;
    @BindView(R.id.tv_weight_range_text)
    TextView tv_weight_range_text;
    @BindView(R.id.tv_height_range_text)
    TextView tv_height_range_text;

    @BindString(R.string.range_text_separator)
    String range_text_separator;
    @BindString(R.string.age_range_default_text)
    String age_range_default_text;
    @BindString(R.string.weight_range_default_text)
    String weight_range_default_text;
    @BindString(R.string.weight_range_text)
    String weight_range_text;
    @BindString(R.string.height_range_default_text)
    String height_range_default_text;
    @BindString(R.string.height_range_text)
    String height_range_text;
    private Unbinder unbinder;

    private FilterListener filterListener;

    public HeroFilterFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.configureViews();
    }

    private void configureViews() {
        this.rb_age.setOnRangeBarChangeListener(this);
        this.rb_weight.setOnRangeBarChangeListener(this);
        this.rb_height.setOnRangeBarChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getComponent(HeroComponent.class).inject(this);
        final View fragmentView = inflater.inflate(R.layout.fragment_hero_filter, container, false);
        this.unbinder = ButterKnife.bind(this, fragmentView);
        this.heroFilterPresenter.setView(this);
        this.loadFilterValues();
        return fragmentView;
    }

    private void loadFilterValues() {
        this.heroFilterPresenter.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.heroFilterPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.heroFilterPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.heroFilterPresenter.destroy();
    }

    @NonNull
    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void restartNameFilter() {
        this.et_name.clear();
    }

    @Override
    public void restartAgeFilter(int minAge, int maxAge) {
        this.rb_age.setTickCount(maxAge + 1);
        this.rb_age.setThumbIndices(minAge, maxAge);
    }

    @Override
    public void showAllAges() {
        this.tv_age_range_text.setText(this.age_range_default_text);
    }

    @Override
    public void showEqualAge(int age) {
        String plural = getResources().getQuantityString(R.plurals.age_range_text, age);
        String text = String.format(plural, age);
        this.tv_age_range_text.setText(text);
    }

    @Override
    public void showAgeRange(int from, int to) {
        String pluralFrom = getResources().getQuantityString(R.plurals.age_range_text, from);
        String pluralTo = getResources().getQuantityString(R.plurals.age_range_text, to);
        String textFrom = String.format(pluralFrom, from);
        String textTo = String.format(pluralTo, to);
        this.tv_age_range_text.setText(textFrom + range_text_separator + textTo);
    }

    @Override
    public void restartWeightFilter(int minWeight, int maxWeight) {
        this.rb_weight.setTickCount(maxWeight + 1);
        this.rb_weight.setThumbIndices(minWeight, maxWeight);
    }

    @Override
    public void showAllWeights() {
        this.tv_weight_range_text.setText(this.weight_range_default_text);
    }

    @Override
    public void showEqualWeight(int weight) {
        String text = String.format(this.weight_range_text, weight);
        this.tv_weight_range_text.setText(text);
    }

    @Override
    public void showWeightRange(int from, int to) {
        String textFrom = String.format(this.weight_range_text, from);
        String textTo = String.format(this.weight_range_text, to);
        this.tv_weight_range_text.setText(textFrom + range_text_separator + textTo);
    }

    @Override
    public void restartHeightFilter(int minHeight, int maxHeight) {
        this.rb_height.setTickCount(maxHeight + 1);
        this.rb_height.setThumbIndices(minHeight, maxHeight);
    }

    @Override
    public void showAllHeights() {
        this.tv_height_range_text.setText(this.height_range_default_text);
    }

    @Override
    public void showEqualHeight(int height) {
        String text = String.format(this.height_range_text, height);
        this.tv_height_range_text.setText(text);
    }

    @Override
    public void showHeightRange(int from, int to) {
        String textFrom = String.format(this.height_range_text, from);
        String textTo = String.format(this.height_range_text, to);
        this.tv_height_range_text.setText(textFrom + range_text_separator + textTo);
    }

    @Override
    public void filter(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                       Integer minHeight, Integer maxHeight) {
        if(this.filterListener != null) {
            this.filterListener.onFilter(name, minAge, maxAge, minWeight, maxWeight, minHeight, maxHeight);
        }
    }

    @OnClick(R.id.btn_apply_filters)
    public void onApplyFilterClick() {
        this.heroFilterPresenter.filter(
                this.et_name.getText().toString(),
                this.rb_age.getLeftIndex(),
                this.rb_age.getRightIndex(),
                this.rb_weight.getLeftIndex(),
                this.rb_weight.getRightIndex(),
                this.rb_height.getLeftIndex(),
                this.rb_height.getRightIndex()
        );
    }

    @OnClick(R.id.btn_remove_filters)
    public void onRemoveFiltersClick() {
        this.heroFilterPresenter.restartFilters();
    }

    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int left, int right) {
        if (rangeBar == this.rb_age) {
            this.heroFilterPresenter.ageRangeChange(left, right);
        } else if (rangeBar == this.rb_weight) {
            this.heroFilterPresenter.weightRangeChange(left, right);
        } else {
            this.heroFilterPresenter.heightRangeChange(left, right);
        }
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    /**
     * Interface for listening filter events.
     */
    public interface FilterListener {
        void onFilter(final String name, final Integer minAge, final Integer maxAge, final Integer minWeight,
                      final Integer maxWeight, final Integer minHeight, final Integer maxHeight);
    }
}