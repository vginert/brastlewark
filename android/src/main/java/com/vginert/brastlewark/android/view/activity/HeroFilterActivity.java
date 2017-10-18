package com.vginert.brastlewark.android.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.HasComponent;
import com.vginert.brastlewark.android.di.components.DaggerHeroComponent;
import com.vginert.brastlewark.android.di.components.HeroComponent;
import com.vginert.brastlewark.android.view.fragment.HeroFilterFragment;

/**
 * Fragment that shows a filter for the list of Heroes.
 *
 * @author Vicente Giner Tendero
 */
public class HeroFilterActivity extends BaseActivity implements HasComponent<HeroComponent>,
        HeroFilterFragment.FilterListener {

    public static final String INTENT_EXTRA_PARAM_HERO_NAME = "INTENT_EXTRA_PARAM_HERO_NAME";
    public static final String INTENT_EXTRA_PARAM_HERO_MIN_AGE = "INTENT_EXTRA_PARAM_HERO_MIN_AGE";
    public static final String INTENT_EXTRA_PARAM_HERO_MAX_AGE = "INTENT_EXTRA_PARAM_HERO_MAX_AGE";
    public static final String INTENT_EXTRA_PARAM_HERO_MIN_WEIGHT = "INTENT_EXTRA_PARAM_HERO_MIN_WEIGHT";
    public static final String INTENT_EXTRA_PARAM_HERO_MAX_WEIGHT = "INTENT_EXTRA_PARAM_HERO_MAX_WEIGHT";
    public static final String INTENT_EXTRA_PARAM_HERO_MIN_HEIGHT = "INTENT_EXTRA_PARAM_HERO_MIN_HEIGHT";
    public static final String INTENT_EXTRA_PARAM_HERO_MAX_HEIGHT = "INTENT_EXTRA_PARAM_HERO_MAX_HEIGHT";

    private HeroComponent heroComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            HeroFilterFragment heroFilterFragment = new HeroFilterFragment();
            heroFilterFragment.setFilterListener(this);
            addFragment(R.id.fragmentContainer, heroFilterFragment);
        }
    }

    private void initializeInjector() {
        this.heroComponent = DaggerHeroComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public HeroComponent getComponent() {
        return this.heroComponent;
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HeroFilterActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFilter(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                         Integer minHeight, Integer maxHeight) {
        Intent returnIntent = new Intent();
        if(name != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_NAME, name);
        }
        if(minAge != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_MIN_AGE, minAge);
        }
        if(maxAge != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_MAX_AGE, maxAge);
        }
        if(minWeight != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_MIN_WEIGHT, minWeight);
        }
        if(maxWeight != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_MAX_WEIGHT, maxWeight);
        }
        if(minHeight != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_MIN_HEIGHT, minHeight);
        }
        if(maxHeight != null) {
            returnIntent.putExtra(INTENT_EXTRA_PARAM_HERO_MAX_HEIGHT, maxHeight);
        }
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
