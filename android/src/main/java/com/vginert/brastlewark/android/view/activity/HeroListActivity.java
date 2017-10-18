package com.vginert.brastlewark.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.HasComponent;
import com.vginert.brastlewark.android.di.components.DaggerHeroComponent;
import com.vginert.brastlewark.android.di.components.HeroComponent;
import com.vginert.brastlewark.android.model.HeroModel;
import com.vginert.brastlewark.android.view.fragment.HeroListFragment;

/**
 * Activity that shows a list of Heros.
 *
 * @author Vicente Giner Tendero
 */

public class HeroListActivity extends BaseActivity implements HasComponent<HeroComponent>,
        HeroListFragment.HeroListListener, ToolbarActivity {

    private HeroComponent heroComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        setContentView(R.layout.activity_layout);
        if (savedInstanceState == null) {
            HeroListFragment heroListFragment = new HeroListFragment();
            addFragment(R.id.fragmentContainer, heroListFragment);
        }
    }

    private void initializeInjector() {
        this.heroComponent = DaggerHeroComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hero_list_menu, menu);
        return true;
    }

    @Override
    public HeroComponent getComponent() {
        return this.heroComponent;
    }

    @Override
    public void onHeroClicked(HeroModel heroModel) {
        this.navigator.navigateToHeroDetails(this, heroModel.getId());
    }

    @Override
    public void onFilterClicked(Fragment fragment, int requestCode) {
        this.navigator.openToHeroFilter(fragment, this, requestCode);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public ActionBar getToolbar() {
        return getSupportActionBar();
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HeroListActivity.class);
    }
}
