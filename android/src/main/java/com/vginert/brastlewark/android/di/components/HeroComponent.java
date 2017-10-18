package com.vginert.brastlewark.android.di.components;

import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.android.di.modules.ActivityModule;
import com.vginert.brastlewark.android.di.modules.HeroModule;
import com.vginert.brastlewark.android.view.fragment.HeroDetailsFragment;
import com.vginert.brastlewark.android.view.fragment.HeroFilterFragment;
import com.vginert.brastlewark.android.view.fragment.HeroListFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects hero specific Fragments.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, HeroModule.class})
public interface HeroComponent extends ActivityComponent {

    void inject(HeroListFragment heroListFragment);

    void inject(HeroDetailsFragment heroDetailsFragment);

    void inject(HeroFilterFragment heroFilterFragment);
}
