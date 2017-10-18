package com.vginert.brastlewark.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.HasComponent;
import com.vginert.brastlewark.android.di.components.DaggerHeroComponent;
import com.vginert.brastlewark.android.di.components.HeroComponent;
import com.vginert.brastlewark.android.view.fragment.HeroDetailsFragment;

/**
 * Activity that shows details of a certain hero.
 *
 * @author Vicente Giner Tendero
 */

public class HeroDetailsActivity extends BaseActivity implements HasComponent<HeroComponent>,
        ToolbarActivity {

    private static final String INTENT_EXTRA_PARAM_HERO_ID = "com.vginert.brastlewark.android.INTENT_PARAM_HERO_ID";
    private static final String INSTANCE_STATE_PARAM_HERO_ID = "com.vginert.brastlewark.android.STATE_PARAM_HERO_ID";

    private int heroId;
    private HeroComponent heroComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_HERO_ID, this.heroId);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.heroId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_HERO_ID, -1);
            addFragment(R.id.fragmentContainer, HeroDetailsFragment.forHero(heroId));
        } else {
            this.heroId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_HERO_ID);
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
        return heroComponent;
    }

    public static Intent getCallingIntent(Context context, int heroId) {
        Intent callingIntent = new Intent(context, HeroDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_HERO_ID, heroId);
        return callingIntent;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public ActionBar getToolbar() {
        return getSupportActionBar();
    }
}
