package com.vginert.brastlewark.android.di.components;

import android.app.Activity;

import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.android.di.modules.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
