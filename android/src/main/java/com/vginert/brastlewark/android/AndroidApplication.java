package com.vginert.brastlewark.android;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.vginert.brastlewark.android.di.components.ApplicationComponent;
import com.vginert.brastlewark.android.di.components.DaggerApplicationComponent;
import com.vginert.brastlewark.android.di.modules.ApplicationModule;

/**
 * Android Main Application
 *
 * @author Vicente Giner Tendero
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    this.initializeInjector();
    this.initializeLeakDetection();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  private void initializeLeakDetection() {
    if (BuildConfig.DEBUG) {
      LeakCanary.install(this);
    }
  }
}
