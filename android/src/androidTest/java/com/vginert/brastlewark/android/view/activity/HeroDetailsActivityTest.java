package com.vginert.brastlewark.android.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

import com.vginert.brastlewark.android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeroDetailsActivityTest extends ActivityInstrumentationTestCase2<HeroDetailsActivity> {

    private static final int FAKE_USER_ID = 10;

    private HeroDetailsActivity heroDetailsActivity;

    public HeroDetailsActivityTest() {
        super(HeroDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        this.heroDetailsActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsHeroDetailsFragment() {
        Fragment heroDetailsFragment =
                heroDetailsActivity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assertThat(heroDetailsFragment, is(notNullValue()));
    }

    public void testContainsProperTitle() {
        String actualTitle = this.heroDetailsActivity.getTitle().toString().trim();

        assertThat(actualTitle, is("Jon Snow"));
    }

    public void testLoadHeroHappyCaseViews() {
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_avatar)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_age)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_weight)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_height)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_hair_color)).check(matches(isDisplayed()));
    }

    public void testLoadHeroHappyCaseData() {
        onView(withId(R.id.tv_name)).check(matches(withText("Jon Snow")));
        onView(withId(R.id.tv_age)).check(matches(withText("22")));
    }

    private Intent createTargetIntent() {
        Intent intentLaunchActivity =
                HeroDetailsActivity.getCallingIntent(getInstrumentation().getTargetContext(), FAKE_USER_ID);

        return intentLaunchActivity;
    }
}