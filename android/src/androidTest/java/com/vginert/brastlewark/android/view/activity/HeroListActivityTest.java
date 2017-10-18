package com.vginert.brastlewark.android.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

import com.vginert.brastlewark.android.R;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeroListActivityTest extends ActivityInstrumentationTestCase2<HeroListActivity> {

    private HeroListActivity heroListActivity;

    public HeroListActivityTest() {
        super(HeroListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        heroListActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsHeroListFragment() {
        Fragment heroListFragment =
                heroListActivity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assertThat(heroListFragment, is(notNullValue()));
    }

    public void testContainsProperTitle() {
        String actualTitle = this.heroListActivity.getTitle().toString().trim();

        assertThat(actualTitle, is(heroListActivity.getResources().getString(R.string.app_name)));
    }

    private Intent createTargetIntent() {
        return HeroListActivity.getCallingIntent(getInstrumentation().getTargetContext());
    }
}