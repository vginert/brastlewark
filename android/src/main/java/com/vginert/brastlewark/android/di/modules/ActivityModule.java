package com.vginert.brastlewark.android.di.modules;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.vginert.brastlewark.android.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 *
 * @author Vicente Giner Tendero
 */
@Module
public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    /**
     * Expose the fragment manager to dependents in the graph.
     */
    @Provides
    @PerActivity
    FragmentManager fragmentManager() {
        return this.activity.getSupportFragmentManager();
    }
}
