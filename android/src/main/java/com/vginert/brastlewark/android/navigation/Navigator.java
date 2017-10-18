package com.vginert.brastlewark.android.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.vginert.brastlewark.android.view.activity.HeroDetailsActivity;
import com.vginert.brastlewark.android.view.activity.HeroFilterActivity;
import com.vginert.brastlewark.android.view.activity.HeroListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 *
 * @author Vicente Giner Tendero
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    /**
     * Goes to the hero list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToHeroList(Context context) {
        if (context != null) {
            Intent intentToLaunch = HeroListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the hero details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToHeroDetails(Context context, int heroId) {
        if (context != null) {
            Intent intentToLaunch = HeroDetailsActivity.getCallingIntent(context, heroId);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the hero details screen.
     *
     * @param activity    A Activity needed to open the destiny activity for result.
     * @param context     A Context needed to open the destiny activity.
     * @param requestCode A code that represent the result request.
     */
    public void openToHeroFilter(Activity activity, Context context, final int requestCode) {
        if (context != null && activity != null) {
            Intent intentToLaunch = HeroFilterActivity.getCallingIntent(context);
            activity.startActivityForResult(intentToLaunch, requestCode);
        }
    }

    /**
     * Goes to the hero details screen.
     *
     * @param fragment    A Fragment needed to open the destiny activity for result.
     * @param context     A Context needed to open the destiny activity.
     * @param requestCode A code that represent the result request.
     */
    public void openToHeroFilter(Fragment fragment, Context context, final int requestCode) {
        if (context != null && fragment != null) {
            Intent intentToLaunch = HeroFilterActivity.getCallingIntent(context);
            fragment.startActivityForResult(intentToLaunch, requestCode);
        }
    }
}
